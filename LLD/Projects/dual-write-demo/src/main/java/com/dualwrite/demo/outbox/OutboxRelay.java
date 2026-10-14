package com.dualwrite.demo.outbox;

import com.dualwrite.demo.common.EventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * The "Message Relay" half of the transactional outbox pattern.
 *
 * Polls for PENDING outbox rows and publishes each one to the broker. This
 * is where at-least-once delivery is implemented:
 *  - if publish() throws, the row is left/marked so it will be retried on
 *    the next poll -- nothing is lost.
 *  - if the relay crashes mid-batch, unpublished rows simply remain PENDING
 *    and get picked up after restart.
 *  - marking a row PUBLISHED happens only after publish() returns
 *    successfully, and is committed in its own small transaction so a
 *    crash between "publish succeeded" and "mark published" can, at worst,
 *    cause a duplicate publish on restart -- never a lost one. Consumers
 *    must therefore be idempotent (dedupe on eventId), which is the
 *    standard trade-off of at-least-once delivery.
 *
 * In production this polling loop is typically replaced by log-based CDC
 * (e.g. Debezium tailing the DB's write-ahead log) instead of a SELECT
 * poll -- see the README for that comparison.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxRelay {

    private final OutboxEventRepository outboxEventRepository;
    private final EventPublisher eventPublisher;

    private static final int MAX_RETRIES = 5;

    @Scheduled(fixedDelayString = "${app.outbox.poll-interval-ms:2000}")
    public void relay() {
        List<OutboxEvent> pending = outboxEventRepository
                .findTop50ByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);

        if (pending.isEmpty()) {
            return;
        }
        log.info("[OUTBOX-RELAY] found {} pending event(s)", pending.size());

        for (OutboxEvent event : pending) {
            publishOne(event);
        }
    }

    @Transactional
    public void publishOne(OutboxEvent event) {
        try {
            eventPublisher.publish(event.getTopic(), event.getAggregateKey(), event.getPayload());
            event.setStatus(OutboxStatus.PUBLISHED);
            event.setPublishedAt(Instant.now());
            outboxEventRepository.save(event);
            log.info("[OUTBOX-RELAY] published event {} (order {})", event.getId(), event.getAggregateKey());
        } catch (Exception e) {
            int retries = event.getRetryCount() == null ? 0 : event.getRetryCount();
            event.setRetryCount(retries + 1);
            if (retries + 1 >= MAX_RETRIES) {
                event.setStatus(OutboxStatus.FAILED);
                log.error("[OUTBOX-RELAY] event {} FAILED after {} retries: {}",
                        event.getId(), retries + 1, e.getMessage());
            } else {
                log.warn("[OUTBOX-RELAY] publish attempt {} failed for event {}, will retry: {}",
                        retries + 1, event.getId(), e.getMessage());
            }
            outboxEventRepository.save(event);
        }
    }
}
