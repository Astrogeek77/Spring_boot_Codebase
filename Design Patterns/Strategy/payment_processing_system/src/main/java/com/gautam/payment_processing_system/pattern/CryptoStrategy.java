package com.gautam.payment_processing_system.pattern;

import org.springframework.stereotype.Component;

@Component("crypto")
public class CryptoStrategy implements PaymentStrategy {
    @Override
    public String pay(double amount) {
        // Complex logic for blockchain wallet transactions would go here
        return String.format("Successfully processed $%.2f via Cryptocurrency.", amount);
    }
}
