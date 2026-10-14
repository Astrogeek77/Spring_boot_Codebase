package com.dualwrite.demo.naive;

import com.dualwrite.demo.common.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/**
 * THE PROBLEM, illustrated directly.
 *
 * saveThenPublish() writes the Order row in its own database transaction
 * and then, separately, calls the broker. These are two independent
 * operations against two independent systems (Postgres and Kafka) with no
 * shared transaction coordinator between them. Either step can succeed
 * while the other fails:
 *
 *   1. DB write commits, broker publish throws / times out / the process
 *      is killed right after commit.
 *      -> Order exists in the database but no downstream system
 *         (inventory, billing, notifications) ever hears about it.
 *
 *   2. Broker publish succeeds, but the DB write never happens (crash,
 *      validation failure, connection drop before the insert).
 *      -> Downstream systems process an order that does not exist in the
 *         source of truth.
 *
 * Both are the "dual write problem": no atomicity across heterogeneous
 * systems. The DB write below uses Propagation.REQUIRES_NEW specifically
 * so it commits independently and immediately -- exactly like two separate
 * calls in naive production code would behave, and so the fault injection
 * below produces a *real*, durable inconsistency rather than something
 * Spring's default rollback-on-exception behavior would quietly undo.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NaiveOrderService {

    private final EventPublisher eventPublisher;
    private final NaiveOrderWriter naiveOrderWriter;

    public static final String TOPIC = "order-events";

    public Order saveThenPublish(CreateOrderRequest request) {
        CreateOrderRequest.FailureMode failureMode =
                request.simulateFailure() == null ? CreateOrderRequest.FailureMode.NONE : request.simulateFailure();

        // --- Failure mode B: broker succeeds, DB write never happens ------
        // Publish first, exactly like a naive "notify, then persist" ordering
        // would. The DB write below is never even attempted, so no row is
        // ever created -- downstream now believes in an order that doesn't
        // exist in the source of truth.
        if (failureMode == CreateOrderRequest.FailureMode.CRASH_AFTER_PUBLISH) {
            String phantomOrderId = UUID.randomUUID().toString();
            OrderCreatedEvent event = new OrderCreatedEvent(
                    UUID.randomUUID().toString(), phantomOrderId, request.customerName(),
                    request.productSku(), request.quantity(), request.totalAmount(), Instant.now());
            eventPublisher.publish(TOPIC, phantomOrderId, event);
            log.error("[NAIVE] Broker publish succeeded for order {}, now simulating the DB write failing. "
                    + "No row will ever exist for this order.", phantomOrderId);
            throw new DualWriteFailureException(
                    "Simulated failure: broker publish succeeded for order " + phantomOrderId
                            + ", but the database write that should have followed never happened. "
                            + "Downstream systems now believe in an order that does not exist.");
        }

        // --- Write #1: the database (own transaction, commits independently) ---
        Order order = Order.builder()
                .customerName(request.customerName())
                .productSku(request.productSku())
                .quantity(request.quantity())
                .totalAmount(request.totalAmount())
                .status(OrderStatus.CREATED)
                .createdAt(Instant.now())
                .source("NAIVE")
                .build();
        order = naiveOrderWriter.save(order);
        log.info("[NAIVE] DB write committed for order {}", order.getId());

        // --- Failure mode A: DB committed, broker call never happens -------
        if (failureMode == CreateOrderRequest.FailureMode.CRASH_AFTER_DB_COMMIT) {
            log.error("[NAIVE] Simulated crash AFTER DB commit, BEFORE publish. "
                    + "Order {} now exists in DB with no event ever published.", order.getId());
            throw new DualWriteFailureException(
                    "Simulated failure: order " + order.getId() + " was committed to the database, "
                            + "but the broker publish that should have followed never happened. "
                            + "It is now permanently un-notified unless a reconciliation job finds it.");
        }

        // --- Write #2: the broker ----------------------------------------
        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID().toString(), order.getId(), order.getCustomerName(),
                order.getProductSku(), order.getQuantity(), order.getTotalAmount(), Instant.now());
        eventPublisher.publish(TOPIC, order.getId(), event);
        log.info("[NAIVE] Broker publish succeeded for order {}", order.getId());
        return order;
    }
}
