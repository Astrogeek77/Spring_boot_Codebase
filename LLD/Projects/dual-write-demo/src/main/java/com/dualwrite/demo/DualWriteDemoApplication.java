package com.dualwrite.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Demonstrates the "Dual Write Problem": a service that must update its own
 * database AND publish an event to a message broker as if it were a single
 * atomic operation, even though the two systems do not share a transaction.
 *
 * Two implementations are exposed side by side:
 *  - /api/naive/**   : writes DB then publishes to the broker directly.
 *                      Can be made to fail between the two steps to show
 *                      the resulting inconsistency.
 *  - /api/outbox/**  : writes DB row + outbox row in one local transaction,
 *                      then a background relay publishes from the outbox.
 *                      This removes the dual write problem entirely.
 */
@SpringBootApplication
@EnableScheduling
public class DualWriteDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DualWriteDemoApplication.class, args);
    }
}
