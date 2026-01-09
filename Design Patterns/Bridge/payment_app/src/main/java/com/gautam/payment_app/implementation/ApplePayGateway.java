package com.gautam.payment_app.implementation;

import org.springframework.stereotype.Component;

@Component
public class ApplePayGateway implements PaymentGateway {
    @Override
    public void processPayment(String paymentType) {
        System.out.println("Apple: Processing " + paymentType + " request securely.");
    }
}
