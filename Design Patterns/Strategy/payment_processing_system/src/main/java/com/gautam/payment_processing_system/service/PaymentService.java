package com.gautam.payment_processing_system.service;

import com.gautam.payment_processing_system.pattern.PaymentStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentService {

    // Spring injects all beans implementing PaymentStrategy here automatically!
    private final Map<String, PaymentStrategy> paymentStrategies;

    @Autowired
    public PaymentService(Map<String, PaymentStrategy> paymentStrategies) {
        this.paymentStrategies = paymentStrategies;
    }

    public String executePayment(String paymentMethod, double amount) {
        // Look up the strategy dynamically based on user input
        PaymentStrategy strategy = paymentStrategies.get(paymentMethod.toLowerCase());

        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod);
        }

        // Delegate the work to the selected strategy
        return strategy.pay(amount);
    }
}
