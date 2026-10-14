package com.gautam.SOPE.orderengine.listener;

import com.gautam.SOPE.orderengine.event.OrderPlacedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class OrderEventListener {

    // Listener 1: Email Service
    @Async // This runs in a separate thread!
    @EventListener
    public void handleEmailNotification(OrderPlacedEvent event) {
        try {
            // Simulate a slow network call (2 seconds)
            System.out.println("2. Email Listener: Sending confirmation email...");
            Thread.sleep(2000);
            System.out.println("3. Email Listener: Email sent for Order.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Listener 2: Inventory Service
    @Async // Runs in parallel with Email
    @EventListener
    public void handleInventoryUpdate(OrderPlacedEvent event) {
        System.out.println("2. Inventory Listener: Deducting stock...");
    }
}