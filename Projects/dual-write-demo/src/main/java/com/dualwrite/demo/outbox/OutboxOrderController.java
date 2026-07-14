package com.dualwrite.demo.outbox;

import com.dualwrite.demo.common.CreateOrderRequest;
import com.dualwrite.demo.common.Order;
import com.dualwrite.demo.common.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OutboxOrderController {

    private final OutboxOrderService outboxOrderService;
    private final OrderRepository orderRepository;
    private final OutboxEventRepository outboxEventRepository;

    @PostMapping("/api/outbox/orders")
    public ResponseEntity<Order> create(@Valid @RequestBody CreateOrderRequest request) {
        Order order = outboxOrderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }

    @GetMapping("/api/outbox/orders")
    public List<Order> list() {
        return orderRepository.findAll().stream()
                .filter(o -> "OUTBOX".equals(o.getSource()))
                .toList();
    }

    /** Inspect the outbox table directly to watch PENDING -> PUBLISHED transitions. */
    @GetMapping("/api/outbox/events")
    public List<OutboxEvent> events() {
        return outboxEventRepository.findAll();
    }
}
