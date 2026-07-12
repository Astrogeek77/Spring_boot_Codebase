package com.gautam.payment_processing_system.pattern;

import org.springframework.stereotype.Component;

@Component("paypal")
public class PayPalStrategy implements PaymentStrategy {
    @Override
    public String pay(double amount) {
        // Complex logic for redirecting to PayPal OAuth would go here
        return String.format("Successfully processed $%.2f via PayPal.", amount);
    }
}
