package com.gautam.internet_firewall.service;

public class RealInternet implements Internet {

    @Override
    public void connectTo(String serverhost) {
        // Simulate a real connection
        System.out.println("Connecting to " + serverhost);
    }
}
