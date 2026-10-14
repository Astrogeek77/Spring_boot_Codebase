package com.dualwrite.demo.common;

/**
 * Abstraction over "publish to the message broker". In the h2 profile this
 * is backed by a logging stub so the whole demo runs without any external
 * infrastructure. In the docker profile it is backed by a real Kafka
 * producer (see KafkaEventPublisher), which is what makes the failure
 * scenarios genuinely observable (broker down, network partition, etc.)
 * instead of just simulated in code.
 */
public interface EventPublisher {
    void publish(String topic, String key, Object payload);
}
