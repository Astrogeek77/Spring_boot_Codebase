package com.gautam.payment_processing_system.pattern;

import org.springframework.stereotype.Component;

@Component("credit_card")
public class CreditCardStrategy implements PaymentStrategy {
    @Override
    public String pay(double amount) {
        // Complex logic for connecting to Visa/Mastercard APIs would go here
        return String.format("Successfully processed $%.2f via Credit Card.", amount);
    }
}
