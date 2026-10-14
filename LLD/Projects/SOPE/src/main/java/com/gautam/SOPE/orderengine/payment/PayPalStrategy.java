package com.gautam.SOPE.orderengine.payment;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class PayPalStrategy implements PaymentStrategy {
    @Override
    public void processPayment(BigDecimal amount) {
        System.out.println("Redirecting to PayPal for amount: " + amount);
    }

    @Override
    public String getPaymentType() {
        return "PAYPAL";
    }
}
