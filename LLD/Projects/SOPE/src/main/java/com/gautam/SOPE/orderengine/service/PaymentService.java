package com.gautam.SOPE.orderengine.service;

import com.gautam.SOPE.orderengine.payment.PaymentStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final Map<String, PaymentStrategy> paymentStrategies;

    // Constructor Injection: Spring auto-magically finds all classes implementing PaymentStrategy
    // and puts them into a List. We then convert that List to a Map for easy lookup.
    public PaymentService(List<PaymentStrategy> strategies) {
        this.paymentStrategies = strategies.stream()
                .collect(Collectors.toMap(PaymentStrategy::getPaymentType, Function.identity()));
    }

    public void checkout(String paymentType, BigDecimal amount) {
        PaymentStrategy strategy = paymentStrategies.get(paymentType);

        if (strategy == null) {
            throw new IllegalArgumentException("Invalid payment type: " + paymentType);
        }

        strategy.processPayment(amount);
    }
}
