package com.dualwrite.demo.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * Stand-in broker used when app.messaging.mode=LOG (default h2 profile).
 * Lets you read/run the whole demo without standing up Kafka.
 * Switch to KafkaEventPublisher (app.messaging.mode=KAFKA, docker profile)
 * to see the real dual-write failure modes against an actual broker.
 */
@Slf4j
@Component
@ConditionalOnProperty(name = "app.messaging.mode", havingValue = "LOG", matchIfMissing = true)
public class LoggingEventPublisher implements EventPublisher {
    @Override
    public void publish(String topic, String key, Object payload) {
        log.info("[LOG-BROKER] topic={} key={} payload={}", topic, key, payload);
    }
}
