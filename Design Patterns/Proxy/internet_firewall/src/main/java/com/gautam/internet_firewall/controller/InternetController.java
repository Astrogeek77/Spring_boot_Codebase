package com.gautam.internet_firewall.controller;

import com.gautam.internet_firewall.service.Internet;
import com.gautam.internet_firewall.service.ProxyInternet;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class InternetController {

    @GetMapping("/api/proxy/browse")
    public List<String> browseWeb(@RequestParam String site) {
        List<String> logs = new ArrayList<>();

        // The Client talks to the Interface, but we give them the Proxy
        Internet internet = new ProxyInternet();

        try {
            internet.connectTo(site);
            logs.add("SUCCESS: Connected to " + site);
        } catch (Exception e) {
            logs.add("BLOCKED: " + e.getMessage());
        }

        return logs;
    }
}
