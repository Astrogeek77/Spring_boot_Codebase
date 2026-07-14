package com.dualwrite.demo.kafka;

import com.dualwrite.demo.common.EventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Real Kafka producer. Active only when app.messaging.mode=KAFKA
 * (the "docker" profile). This is what turns the naive controller's
 * fault-injection flags into genuinely observable dual-write failures:
 * if the broker is unreachable, this call throws, exactly like a real
 * production incident.
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.messaging.mode", havingValue = "KAFKA")
public class KafkaEventPublisher implements EventPublisher {

    // Raw type deliberately: Spring Boot's autoconfigured KafkaTemplate bean
    // is built from KafkaProperties as KafkaTemplate<Object, Object>, so
    // injecting a narrower generic (e.g. <String, String>) here can fail
    // generics-aware autowiring. Raw type matches by class regardless.
    @SuppressWarnings({"rawtypes", "unchecked"})
    private final KafkaTemplate kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public void publish(String topic, String key, Object payload) {
        // Outbox events arrive already-serialized (String); naive-flow
        // events arrive as objects and need serializing here.
        String json = payload instanceof String s ? s : objectMapper.writeValueAsString(payload);

        // .get() forces synchronous behavior so failures surface immediately
        // to the caller, matching how a naive service would call Kafka inline.
        try {
            kafkaTemplate.send(topic, key, json).get();
            log.info("[KAFKA] published topic={} key={}", topic, key);
        } catch (Exception e) {
            log.error("[KAFKA] publish FAILED topic={} key={} reason={}", topic, key, e.getMessage());
            throw new RuntimeException("Kafka publish failed: " + e.getMessage(), e);
        }
    }
}
