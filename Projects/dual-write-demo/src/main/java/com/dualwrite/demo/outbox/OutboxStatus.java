package com.dualwrite.demo.outbox;

public enum OutboxStatus {
    PENDING,
    PUBLISHED,
    FAILED
}
