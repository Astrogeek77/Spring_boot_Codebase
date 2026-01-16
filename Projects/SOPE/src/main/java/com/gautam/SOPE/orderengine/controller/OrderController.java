package com.gautam.SOPE.orderengine.controller;

import com.gautam.SOPE.orderengine.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final PaymentService paymentService;

    public OrderController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam String type, @RequestParam BigDecimal amount) {
        paymentService.checkout(type, amount);
        return "Payment processed via " + type;
    }
}