package com.gautam.internet_firewall.service;

import java.util.ArrayList;
import java.util.List;

public class ProxyInternet implements Internet {

    // Reference to the Real Object
    private RealInternet realInternet = new RealInternet();

    // The "Banned" List
    private static List<String> bannedSites;

    static {
        bannedSites = new ArrayList<>();
        bannedSites.add("facebook.com");
        bannedSites.add("instagram.com");
        bannedSites.add("tiktok.com");
    }

    @Override
    public void connectTo(String serverhost) throws Exception {
        // 1. The Protection Logic
        if (bannedSites.contains(serverhost.toLowerCase())) {
            System.out.println("Access Denied to " + serverhost);
            throw new Exception("Access Denied: This site is blocked by corporate firewall.");
        }

        // 2. If safe, delegate to Real Internet
        realInternet.connectTo(serverhost);
    }
}
