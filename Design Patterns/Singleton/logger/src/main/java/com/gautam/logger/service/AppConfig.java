package com.gautam.logger.service;

public class AppConfig {

    // 1. Static variable to hold the single instance
    private static AppConfig instance;

    // Data we want to share globally
    private int connectionCount = 0;
    private String appStatus = "Running";

    // 2. PRIVATE Constructor
    // This prevents anyone from doing 'new AppConfig()'
    private AppConfig() {
        System.out.println("--> AppConfig Instance Created (This should print ONLY ONCE)");
    }

    // 3. Public Static Accessor (Lazy Initialization)
    // 'synchronized' ensures thread safety (only one thread enters at a time)
    public static synchronized AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    // Business Logic Methods
    public void addConnection() {
        connectionCount++;
    }

    public int getConnectionCount() {
        return connectionCount;
    }

    public String getStatus() {
        return appStatus;
    }
}
