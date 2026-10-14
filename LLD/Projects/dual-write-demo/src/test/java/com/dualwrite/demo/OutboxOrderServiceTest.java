package com.dualwrite.demo;

import com.dualwrite.demo.common.CreateOrderRequest;
import com.dualwrite.demo.common.Order;
import com.dualwrite.demo.common.OrderRepository;
import com.dualwrite.demo.outbox.OutboxEventRepository;
import com.dualwrite.demo.outbox.OutboxOrderService;
import com.dualwrite.demo.outbox.OutboxStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("h2")
class OutboxOrderServiceTest {

    @Autowired
    private OutboxOrderService outboxOrderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OutboxEventRepository outboxEventRepository;

    @Test
    void everyCommittedOrderHasExactlyOneOutboxEvent() {
        CreateOrderRequest request = new CreateOrderRequest(
                "Test Customer", "SKU-123", 2, new BigDecimal("49.99"), null);

        Order order = outboxOrderService.createOrder(request);

        assertThat(orderRepository.findById(order.getId())).isPresent();

        long matchingEvents = outboxEventRepository.findAll().stream()
                .filter(e -> e.getAggregateKey().equals(order.getId()))
                .count();

        assertThat(matchingEvents).isEqualTo(1);

        // At creation time the relay hasn't necessarily run yet, so the
        // event should be PENDING (or already PUBLISHED if the scheduler
        // beat the assertion -- both are valid, FAILED would not be).
        outboxEventRepository.findAll().stream()
                .filter(e -> e.getAggregateKey().equals(order.getId()))
                .findFirst()
                .ifPresent(e -> assertThat(e.getStatus()).isIn(OutboxStatus.PENDING, OutboxStatus.PUBLISHED));
    }
}
