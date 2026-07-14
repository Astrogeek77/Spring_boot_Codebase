package com.dualwrite.demo.outbox;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * A row in the transactional outbox table. Written in the SAME local
 * transaction as the business entity (Order), so it either commits together
 * with the order or not at all -- true atomicity, because it's a single
 * database transaction.
 *
 * A separate relay process (OutboxRelay) later reads unpublished rows and
 * forwards them to the broker, retrying until it succeeds. This shifts the
 * hard problem from "atomically write to two systems" to "reliably deliver
 * an at-least-once message from a table I already committed" -- which is a
 * solved problem.
 */
@Entity
@Table(name = "outbox_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    /** Logical topic/channel this event should be published to. */
    private String topic;

    /** Partitioning / dedup key, typically the aggregate id (order id). */
    private String aggregateKey;

    private String eventType;

    @Column(columnDefinition = "TEXT")
    private String payload;

    @Enumerated(EnumType.STRING)
    private OutboxStatus status;

    private Instant createdAt;

    private Instant publishedAt;

    private Integer retryCount;
}
