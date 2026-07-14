package com.dualwrite.demo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Stands in for a downstream consumer -- e.g. an inventory service or
 * notification service -- reacting to order events. Used to visually prove
 * that:
 *   - the naive flow can leave orders in the DB that this consumer NEVER
 *     sees (CRASH_AFTER_DB_COMMIT), or that this consumer sees for orders
 *     which don't actually exist (CRASH_AFTER_PUBLISH).
 *   - the outbox flow guarantees this consumer eventually sees every
 *     committed order exactly-once-in-effect (at-least-once delivery +
 *     idempotent handling).
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "app.messaging.mode", havingValue = "KAFKA")
public class DownstreamOrderEventConsumer {

    private final List<String> receivedOrderIds = new CopyOnWriteArrayList<>();

    @KafkaListener(topics = "order-events", groupId = "${spring.kafka.consumer.group-id}")
    public void onOrderEvent(String payload) {
        log.info("[DOWNSTREAM] received event: {}", payload);
        receivedOrderIds.add(payload);
    }

    public List<String> getReceivedEvents() {
        return Collections.unmodifiableList(receivedOrderIds);
    }
}
