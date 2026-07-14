package com.dualwrite.demo.naive;

import com.dualwrite.demo.common.CreateOrderRequest;
import com.dualwrite.demo.common.Order;
import com.dualwrite.demo.common.OrderRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Endpoints for the broken/naive flow. Use these to reproduce the dual
 * write problem on demand. See README "Reproducing the Problem" section.
 */
@RestController
@RequestMapping("/api/naive/orders")
@RequiredArgsConstructor
public class NaiveOrderController {

    private final NaiveOrderService naiveOrderService;
    private final OrderRepository orderRepository;

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateOrderRequest request) {
        try {
            Order order = naiveOrderService.saveThenPublish(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (DualWriteFailureException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error", "DUAL_WRITE_INCONSISTENCY",
                            "message", e.getMessage()
                    ));
        }
    }

    @GetMapping
    public List<Order> list() {
        return orderRepository.findAll().stream()
                .filter(o -> "NAIVE".equals(o.getSource()))
                .toList();
    }
}
