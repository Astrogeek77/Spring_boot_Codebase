# Dual Write Demo

A Spring Boot project that **reproduces the dual write problem on demand**, then **fixes it** with the Transactional Outbox pattern — side by side, in the same codebase, so you can compare them directly.

---

## 1. What is the "Dual Write Problem"?

A service often needs to do two things that *look* like one logical operation:

1. Persist state change in its own database (e.g. `INSERT INTO orders`)
2. Notify the rest of the system via a message broker (e.g. publish `OrderCreated` to Kafka)

These are two independent writes to two independent systems (a relational database and a message broker) that **do not share a transaction**. There is no built-in way to make "commit to Postgres" and "publish to Kafka" succeed or fail together. Whichever order you call them in, there's a window where one succeeds and the other doesn't:

```
Write DB, then publish to broker             Publish to broker, then write DB
─────────────────────────────────            ─────────────────────────────────
1. INSERT order  ✅ committed                 1. publish event   ✅ sent
2. publish event ❌ broker down/crash         2. INSERT order    ❌ constraint violation / rollback

Result: order exists, nobody was told         Result: downstream reacts to an
                                               order that doesn't exist
```

This is the **dual write problem**: no atomicity across heterogeneous systems. It's subtle because it only shows up under failure — the happy path always looks correct in testing and demos, which is exactly why it causes production incidents (missed emails, unreserved inventory, unbilled orders, orphaned downstream records).

---

## 2. Project Overview

This project exposes **two parallel implementations of the same "create order" use case**:

| Flow | Endpoint prefix | Behavior |
|---|---|---|
| **Naive** (the problem) | `/api/naive/orders` | Writes the DB row, then calls the broker directly in the same request. Supports fault injection to force the inconsistency on demand. |
| **Outbox** (the fix) | `/api/outbox/orders` | Writes the DB row **and** an outbox row in a single local transaction. A background relay separately, reliably delivers the outbox row to the broker. |

Both flows share the same `Order` entity and `OrderCreatedEvent` payload, so the comparison is apples-to-apples.

### Architecture

```
                      NAIVE FLOW (broken)
 ┌────────────┐   1. INSERT order   ┌────────────┐
 │  Client    │ ───────────────────▶│  Postgres  │
 └─────┬──────┘                     └────────────┘
       │        2. publish (separate call, can fail independently)
       └───────────────────────────▶┌────────────┐
                                     │   Kafka    │
                                     └────────────┘
       No shared transaction between steps 1 and 2.


                      OUTBOX FLOW (fixed)
 ┌────────────┐  single local TX:  ┌──────────────────────────┐
 │  Client    │ ─────────────────▶ │ INSERT order              │
 └────────────┘                    │ INSERT outbox_events row  │──▶ Postgres
                                    │ (same transaction, both   │
                                    │  commit or neither does)  │
                                    └──────────────────────────┘
                                                │
                                                │ polled by
                                                ▼
                                    ┌──────────────────────────┐
                                    │      OutboxRelay          │
                                    │  (@Scheduled poller)      │
                                    │  publish -> mark PUBLISHED│──▶ Kafka
                                    │  retry on failure         │
                                    └──────────────────────────┘
```

The key insight: the outbox flow never makes the DB commit *depend on* the broker being reachable. The only write that must be atomic (order + outbox row) happens inside one database, which already gives you ACID transactions for free. Delivering the outbox row to the broker is then just a retryable, at-least-once background job — a much easier problem.

### Package layout

```
com.dualwrite.demo
├── common/          Order entity, OrderRepository, shared DTOs, EventPublisher interface
├── naive/           NaiveOrderService, NaiveOrderController, DualWriteFailureException
├── outbox/          OutboxEvent, OutboxOrderService, OutboxRelay, OutboxOrderController
├── kafka/           KafkaEventPublisher, KafkaTopicConfig, DownstreamOrderEventConsumer
└── config/          GlobalExceptionHandler
```

---

## 3. Running the Project

### Option A — Quick start, no Docker (H2 + logging "broker")

Runs entirely in-process. The message broker is stubbed by `LoggingEventPublisher`, which just logs what *would* have been published — good for reading the code and API shape quickly, but it can't demonstrate a real broker outage.

```bash
mvn spring-boot:run
```

App starts on `http://localhost:8080` with profile `h2` (the default in `application.yml`). H2 console available at `http://localhost:8080/h2-console` (JDBC URL `jdbc:h2:mem:dualwrite`).

### Option B — Full demo, real Kafka + Postgres (recommended)

This is the version that actually proves the failure modes, because Kafka can genuinely be down.

```bash
docker compose up -d          # starts Postgres, Kafka (KRaft mode), Kafka UI on :8081
mvn spring-boot:run -Dspring-boot.run.profiles=docker
```

- Postgres: `localhost:5432` (db `dualwrite`, user/pass `dualwrite`/`dualwrite`)
- Kafka: `localhost:9092`
- Kafka UI (browse topics/messages): `http://localhost:8081`

To simulate a real broker outage instead of the code-level fault injection:

```bash
docker stop dualwrite-kafka     # take the broker down
curl -X POST localhost:8080/api/naive/orders -H 'Content-Type: application/json' \
  -d '{"customerName":"Amit","productSku":"SKU-1","quantity":1,"totalAmount":499.00}'
# -> DB row is committed, publish throws, request fails with 409 — but the order is now in Postgres
docker start dualwrite-kafka    # bring broker back
```

Do the same against `/api/outbox/orders` while Kafka is down: the order still gets created (201, DB write succeeds), the outbox row stays `PENDING`, and once you `docker start dualwrite-kafka` again, `OutboxRelay` picks it up on its next poll and publishes it — no data loss, no manual intervention.

---

## 4. Reproducing the Problem (fault injection)

`POST /api/naive/orders` accepts a `simulateFailure` field:

| Value | What happens |
|---|---|
| `NONE` (default) | Happy path — DB write then broker publish, both succeed. |
| `CRASH_AFTER_DB_COMMIT` | DB row **commits**, then an exception is thrown before the broker call. Order exists in Postgres; no event was ever published. |
| `CRASH_AFTER_PUBLISH` | Broker publish **succeeds**, then the surrounding transaction is forced to fail, rolling back the DB insert. Downstream systems process an order that doesn't exist in the source of truth. |

Example:

```bash
curl -X POST localhost:8080/api/naive/orders \
  -H 'Content-Type: application/json' \
  -d '{
        "customerName": "Priya",
        "productSku": "SKU-9",
        "quantity": 3,
        "totalAmount": 1499.00,
        "simulateFailure": "CRASH_AFTER_DB_COMMIT"
      }'
```

Response: `409 Conflict` explaining the inconsistency. Then:

```bash
curl localhost:8080/api/naive/orders
```

...shows the order sitting in the database, permanently un-notified (in the `docker` profile, also check `GET /api/downstream/received-events` — it will never contain this order's id).

Do the equivalent against `/api/outbox/orders` (no `simulateFailure` support needed — try killing the Kafka container instead, per section 3) and you'll see the order + outbox row both persist consistently, with the relay catching up once the broker returns.

---

## 5. API Reference

### Naive flow

**`POST /api/naive/orders`**
Create an order via the naive (DB-then-broker) path.

Request body:
```json
{
  "customerName": "string, required",
  "productSku": "string, required",
  "quantity": "integer > 0, required",
  "totalAmount": "decimal > 0, required",
  "simulateFailure": "NONE | CRASH_AFTER_DB_COMMIT | CRASH_AFTER_PUBLISH (optional, default NONE)"
}
```

Responses:
- `201 Created` — order persisted and event published successfully. Body: the created `Order`.
- `409 Conflict` — dual-write inconsistency triggered (either simulated, or a real broker failure in the `docker` profile). Body: `{ "error": "DUAL_WRITE_INCONSISTENCY", "message": "..." }`
- `400 Bad Request` — validation failure. Body: `{ "error": "VALIDATION_FAILED", "fields": { "<field>": "<message>" } }`

**`GET /api/naive/orders`**
List all orders created via the naive flow.

---

### Outbox flow

**`POST /api/outbox/orders`**
Create an order via the outbox pattern (atomic local write, async relay).

Request body: same shape as above (`simulateFailure` is accepted but ignored — the outbox flow has no direct broker call to fail).

Responses:
- `201 Created` — order + outbox row committed atomically. Body: the created `Order`. Note this does **not** guarantee the broker has received the event yet — only that it eventually will.
- `400 Bad Request` — validation failure, same shape as above.

**`GET /api/outbox/orders`**
List all orders created via the outbox flow.

**`GET /api/outbox/events`**
Inspect the outbox table directly — watch rows transition `PENDING → PUBLISHED` (or `→ FAILED` after exhausting retries). Each row includes `retryCount` and `publishedAt`.

---

### Downstream (docker/Kafka profile only)

**`GET /api/downstream/received-events`**
Raw JSON payloads received by the simulated downstream consumer (`DownstreamOrderEventConsumer`), in arrival order. Use this to visually confirm which orders downstream systems actually learned about.

---

## 6. Implementation Details

### `EventPublisher` abstraction
`common/EventPublisher.java` decouples both flows from a specific broker implementation:
- `LoggingEventPublisher` — active when `app.messaging.mode=LOG` (the `h2` profile). Logs instead of publishing, so the whole demo runs without external infrastructure.
- `KafkaEventPublisher` — active when `app.messaging.mode=KAFKA` (the `docker` profile). Wraps `KafkaTemplate` and calls `.get()` on the send future so failures surface synchronously, exactly like a naive inline broker call would in production.

### Naive flow (`naive/NaiveOrderService`)
A single `@Transactional` method that:
1. Saves the `Order` (this commits when the method returns normally, or when Spring flushes/commits the transaction).
2. Optionally throws `DualWriteFailureException` (fault injection point A) to simulate a crash between DB commit and broker call.
3. Publishes the event via `EventPublisher`.
4. Optionally throws after a successful publish (fault injection point B) so the transaction rolls back **after** the broker has already been told.

This mirrors how a lot of real production code is accidentally written — a DB save and a message publish inside what *looks* like a single unit of work, but isn't.

### Outbox flow (`outbox/OutboxOrderService` + `outbox/OutboxRelay`)
- `OutboxOrderService.createOrder()` is `@Transactional` and writes **only to Postgres**: the `orders` row and an `outbox_events` row, in the same transaction. There is no broker call in this method at all — so there is nothing that can desync it.
- `OutboxRelay.relay()` is `@Scheduled` (default every 2s, configurable via `app.outbox.poll-interval-ms`). It polls up to 50 `PENDING` rows, calls `EventPublisher.publish()` for each, and marks each row `PUBLISHED` on success — in its **own** small transaction, separate from the original business transaction.
- On publish failure, `retryCount` is incremented and the row stays `PENDING` (or moves to `FAILED` after `MAX_RETRIES = 5`) so the same event is retried on the next poll rather than lost.
- Because delivery is retried independently of the row that guarantees the order exists, the two systems can never disagree about whether the order "really" happened — only about *when* downstream systems find out, which is an explicit, bounded, monitorable delay instead of a silent permanent loss.

### Idempotency note
At-least-once delivery (the relay may publish the same event twice if it crashes between "broker ack" and "mark PUBLISHED") means **consumers must dedupe** — typically by tracking processed `eventId`s. This project's `DownstreamOrderEventConsumer` is a passive demo consumer and doesn't dedupe; a production consumer would check/store `eventId` before applying side effects.

---

## 7. Algorithms / Patterns for Solving the Dual Write Problem

| Approach | How it works | Pros | Cons | Used here? |
|---|---|---|---|---|
| **Transactional Outbox** (this project) | Write business row + outbox row in one local DB transaction; a separate relay delivers from the outbox. | Simple to reason about; only needs the DB's existing ACID guarantees; broker outages just delay delivery instead of losing/duplicating writes. | Adds an outbox table + relay process; consumers must be idempotent; polling relay adds latency (mitigated by CDC, below). | ✅ Implemented |
| **Outbox + CDC (Debezium)** | Same outbox table, but instead of a polling relay, a Change Data Capture tool tails the database's write-ahead log and streams outbox inserts to Kafka directly. | No polling latency/load; near-real-time; relay logic lives in battle-tested infra (Debezium) instead of app code. | Extra infrastructure (Kafka Connect, Debezium); more operational surface area. | ⚠️ Described only — `OutboxRelay`'s poll loop is the "do it yourself" version of what Debezium automates. |
| **Two-Phase Commit (2PC/XA)** | A distributed transaction coordinator asks both systems to "prepare", then "commit" only if both agree. | True cross-system atomicity, no application-level workaround needed. | Most message brokers (Kafka included) don't support XA well; coordinator is a single point of failure/bottleneck; holds locks across the network, hurting throughput and availability. | ❌ Not used — generally avoided in modern distributed systems for this reason. |
| **Saga / "Listen to yourself"** | The service publishes an event first (to its own DB via outbox, or directly), and *also* consumes that same event to update its own state — the write path and the "notify others" path both flow through one place. More broadly, a Saga breaks a multi-service transaction into a sequence of local transactions with compensating actions for rollback. | Good fit for multi-step business processes spanning several services; explicit compensation logic instead of implicit atomicity. | Higher design complexity; compensating transactions must be carefully designed; eventual consistency across steps. | ❌ Not used — this project is single-service, so a full saga is out of scope, but it's the standard answer once *multiple* services must coordinate. |
| **Event Sourcing** | The database *is* the event log — state is derived by replaying events, so "write to DB" and "publish event" are the same operation by construction. | Eliminates the dual write problem structurally, not procedurally; full audit trail for free. | Major architectural shift; querying current state requires projections; steep learning curve; not a drop-in fix for existing systems. | ❌ Not used — mentioned for completeness; a much bigger commitment than outbox. |
| **Naive dual write** (what most bugs actually look like) | Just call both systems, one after another, in application code with no compensating logic. | Simplest to write. | Not atomic — this is the problem this whole project demonstrates. | ✅ Implemented deliberately as the "before" example. |

**Why Outbox is the default recommendation** for single-service dual-write problems: it needs no new infrastructure beyond a table and a scheduler (or CDC later if you outgrow polling), it reuses guarantees your database already gives you, and it fails safe — the worst case is a delayed or duplicated (but never lost) downstream notification, which idempotent consumers handle cleanly.

---

## 8. Suggested Things to Try

1. Run the `h2` profile, hit `/api/naive/orders` with `CRASH_AFTER_DB_COMMIT`, then `GET /api/naive/orders` — see the orphaned order.
2. Run the `docker` profile, repeat the same call, then check `GET /api/downstream/received-events` — confirm the downstream consumer never saw it.
3. Same `docker` setup: `docker stop dualwrite-kafka`, POST to `/api/outbox/orders`, confirm `201 Created` and `GET /api/outbox/events` shows `PENDING`. `docker start dualwrite-kafka`, wait ~2s, re-check — status flips to `PUBLISHED` and the downstream consumer receives it, with zero data loss.
4. Kill the app process (`Ctrl+C`) right after a `PENDING` outbox row is written but before the relay runs, restart it, and confirm the relay still picks the row up — the outbox table is the durable source of truth, not in-memory state.
