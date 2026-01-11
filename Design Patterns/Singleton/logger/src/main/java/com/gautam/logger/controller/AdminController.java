package com.gautam.logger.controller;

import com.gautam.logger.service.AppConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/stats")
    public String getSystemStats() {
        // Get the SAME Singleton Instance
        AppConfig config = AppConfig.getInstance();

        return "Admin Report: Current Connections = " + config.getConnectionCount();
    }
}