package com.dualwrite.demo.common;

import java.math.BigDecimal;
import java.time.Instant;

public record OrderCreatedEvent(
        String eventId,
        String orderId,
        String customerName,
        String productSku,
        Integer quantity,
        BigDecimal totalAmount,
        Instant occurredAt
) {
}
