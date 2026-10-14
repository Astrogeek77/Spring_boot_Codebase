package com.gautam.SOPE.orderengine.controller;

import com.gautam.SOPE.orderengine.pricing.*;
import com.gautam.SOPE.orderengine.service.OrderService;
import com.gautam.SOPE.orderengine.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final PaymentService paymentService;
    private final OrderService orderService;

    public OrderController(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }

    // ==========================================
    // Phase 1: Strategy Pattern (Payment)
    // ==========================================
    @PostMapping("/checkout")
    public String checkout(@RequestParam String type, @RequestParam BigDecimal amount) {
        paymentService.checkout(type, amount);
        return "Payment processed via " + type;
    }

    // ==========================================
    // Phase 2 & 4: State & Observer (Lifecycle)
    // ==========================================
    @PostMapping("/create")
    public String createOrder() {
        Long id = orderService.createOrder();
        // The Service now handles the Event Publishing internally!
        return "Order created with ID: " + id + " [Status: NewState]. Check logs for Async Email event.";
    }

    @PostMapping("/{id}/next")
    public String nextState(@PathVariable Long id) {
        String newState = orderService.advanceOrderStatus(id);
        return "Order " + id + " moved to: " + newState;
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable Long id) {
        return "Order " + id + " is currently in: " + orderService.getStatus(id);
    }

    // ==========================================
    // Phase 3: Decorator Pattern (Pricing)
    // ==========================================
    @GetMapping("/price")
    public BigDecimal calculatePrice(@RequestParam BigDecimal cost,
                                     @RequestParam(defaultValue = "false") boolean tax,
                                     @RequestParam(defaultValue = "false") boolean pack) {

        // 1. Start with Base Price
        PriceComponent finalPrice = new BasePrice(cost);

        // 2. Wrap Dynamically based on flags
        if (tax) {
            finalPrice = new TaxDecorator(finalPrice);
        }
        if (pack) {
            finalPrice = new PackagingDecorator(finalPrice);
        }

        return finalPrice.getCost();
    }
}