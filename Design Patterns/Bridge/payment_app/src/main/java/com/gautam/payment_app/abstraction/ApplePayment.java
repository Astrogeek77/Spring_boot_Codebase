package com.gautam.payment_app.abstraction;

import com.gautam.payment_app.implementation.PaymentGateway;

public class ApplePayment extends Payment{

    public ApplePayment(PaymentGateway gateway) {
        super(gateway);
    }

    @Override
    public void makePayment() {
        System.out.println("--- Starting Apple Payment ---");
        // Net Banking specific logic
        System.out.println("Step 1: Redirect to Apple Login Page");
        // Delegate actual processing to the bridge
        gateway.processPayment("Apple");
    }
}
