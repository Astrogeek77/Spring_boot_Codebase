package com.gautam.SOPE.orderengine.payment;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BitcoinStrategy implements PaymentStrategy{
    @Override
    public void processPayment(BigDecimal amount) {
        System.out.println("Liquidating Bitcoins for amount: " + amount);
    }

    @Override
    public String getPaymentType() {
        return "BITCOIN";
    }
}
