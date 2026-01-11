package com.gautam.logger.controller;

import com.gautam.logger.service.AppConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/access")
    public String accessApp() {
        // Get the Singleton Instance
        AppConfig config = AppConfig.getInstance();

        // Modify the shared state
        config.addConnection();

        return "User accessed. Total Connections: " + config.getConnectionCount();
    }
}
