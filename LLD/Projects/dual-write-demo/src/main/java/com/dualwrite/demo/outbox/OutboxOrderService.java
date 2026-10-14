package com.dualwrite.demo.outbox;

import com.dualwrite.demo.common.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * THE FIX: Transactional Outbox Pattern.
 *
 * Both the Order row and the OutboxEvent row are written inside the SAME
 * @Transactional method, i.e. the same local database transaction. Either
 * both rows are committed, or neither is -- there is no window in which one
 * exists without the other, because there is only ever one write to one
 * system (the relational DB). The broker is never called from this method
 * at all, so it can never desync the transaction.
 *
 * Delivery to the broker becomes a separate, retryable concern owned by
 * OutboxRelay, which is free to fail and retry as many times as needed
 * without ever risking inconsistency with the Order table.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxOrderService {

    private final OrderRepository orderRepository;
    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    public static final String TOPIC = "order-events";

    @Transactional
    @SneakyThrows
    public Order createOrder(CreateOrderRequest request) {
        Order order = Order.builder()
                .customerName(request.customerName())
                .productSku(request.productSku())
                .quantity(request.quantity())
                .totalAmount(request.totalAmount())
                .status(OrderStatus.CREATED)
                .createdAt(Instant.now())
                .source("OUTBOX")
                .build();
        order = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
                UUID.randomUUID().toString(), order.getId(), order.getCustomerName(),
                order.getProductSku(), order.getQuantity(), order.getTotalAmount(), Instant.now());

        OutboxEvent outboxEvent = OutboxEvent.builder()
                .topic(TOPIC)
                .aggregateKey(order.getId())
                .eventType("OrderCreatedEvent")
                .payload(objectMapper.writeValueAsString(event))
                .status(OutboxStatus.PENDING)
                .createdAt(Instant.now())
                .retryCount(0)
                .build();
        outboxEventRepository.save(outboxEvent);

        // Both rows commit together (or roll back together) when this
        // method returns -- one transaction, one system, no dual write.
        log.info("[OUTBOX] order {} + outbox event {} committed atomically",
                order.getId(), outboxEvent.getId());
        return order;
    }
}
