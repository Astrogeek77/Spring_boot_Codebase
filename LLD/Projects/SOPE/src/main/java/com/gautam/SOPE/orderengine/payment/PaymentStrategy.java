package com.gautam.SOPE.orderengine.payment;

import java.math.BigDecimal;

public interface PaymentStrategy {
    void processPayment(BigDecimal amount);
    String getPaymentType(); // Helper to identify the strategy
}