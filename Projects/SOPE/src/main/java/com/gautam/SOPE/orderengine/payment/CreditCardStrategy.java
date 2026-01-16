package com.gautam.SOPE.orderengine.payment;

import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class CreditCardStrategy implements PaymentStrategy {
    @Override
    public void processPayment(BigDecimal amount) {
        // Real logic would involve an external API call here
        System.out.println("Processing Credit Card payment of: " + amount);
    }

    @Override
    public String getPaymentType() {
        return "CREDIT_CARD";
    }
}
