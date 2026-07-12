package com.gautam.payment_processing_system.controller;

import com.gautam.payment_processing_system.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final PaymentService paymentService;

    @Autowired
    public CheckoutController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public Map<String, Object> processCheckout(@RequestBody CheckoutRequest request) {
        String transactionResult = paymentService.executePayment(request.getPaymentMethod(), request.getAmount());

        Map<String, Object> response = new HashMap<>();
        response.put("status", "SUCCESS");
        response.put("message", transactionResult);
        return response;
    }
}

// DTO Class
class CheckoutRequest {
    private String paymentMethod;
    private double amount;

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}