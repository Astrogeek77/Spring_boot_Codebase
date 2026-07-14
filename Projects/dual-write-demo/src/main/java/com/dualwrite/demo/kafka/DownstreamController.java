package com.dualwrite.demo.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Only present in the docker/KAFKA profile -- lets you see, from the
 * "downstream service" point of view, which order events actually arrived.
 * Compare this against /api/naive/orders or /api/outbox/orders to see the
 * inconsistency (naive) or the guarantee (outbox) directly.
 */
@RestController
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.messaging.mode", havingValue = "KAFKA")
public class DownstreamController {

    private final DownstreamOrderEventConsumer consumer;

    @GetMapping("/api/downstream/received-events")
    public List<String> receivedEvents() {
        return consumer.getReceivedEvents();
    }
}
