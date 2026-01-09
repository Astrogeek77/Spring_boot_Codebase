package com.gautam.payment_app.controller;

import com.gautam.payment_app.abstraction.CardPayment;
import com.gautam.payment_app.abstraction.NetBankingPayment;
import com.gautam.payment_app.abstraction.Payment;
import com.gautam.payment_app.implementation.ApplePayGateway;
import com.gautam.payment_app.implementation.PaypalGateway;
import com.gautam.payment_app.implementation.StripeGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BridgeController {

    private final PaypalGateway paypal;
    private final StripeGateway stripe;
    private final ApplePayGateway apple;

    public BridgeController(PaypalGateway paypal, StripeGateway stripe, ApplePayGateway apple) {
        this.paypal = paypal;
        this.stripe = stripe;
        this.apple = apple;
    }

    @GetMapping("/api/bridge/test")
    public String testBridge() {
        StringBuilder log = new StringBuilder();

        // SCENARIO 1: Card Payment using PayPal
        Payment order1 = new CardPayment(paypal);
        order1.makePayment();
        log.append("Order 1: Card via PayPal executed.<br>");

        // SCENARIO 2: NetBanking using Stripe
        Payment order2 = new NetBankingPayment(stripe);
        order2.makePayment();
        log.append("Order 2: NetBanking via Stripe executed.<br>");

        // SCENARIO 3: Mixing and Matching!
        // We can swap the implementation at runtime easily
        Payment order3 = new CardPayment(stripe);
        order3.makePayment();
        log.append("Order 3: Card via Stripe executed.<br>");

        Payment order4 = new NetBankingPayment(apple);
        order4.makePayment();
        log.append("Order 4: NetBanking via Apple executed.<br>");

        return "<h3>Bridge Pattern Test Results:</h3>" + log.toString() + "<br>Check console for detailed logs.";
    }
}
