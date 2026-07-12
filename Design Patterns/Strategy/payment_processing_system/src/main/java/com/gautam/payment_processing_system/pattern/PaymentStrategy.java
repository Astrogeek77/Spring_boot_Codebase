package com.gautam.payment_processing_system.pattern;

public interface PaymentStrategy {
    // Every payment method must know how to process a payment
    String pay(double amount);
}
