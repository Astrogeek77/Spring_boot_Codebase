package com.dualwrite.demo.common;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateOrderRequest(
        @NotBlank String customerName,
        @NotBlank String productSku,
        @Positive Integer quantity,
        @NotNull @Positive BigDecimal totalAmount,

        /**
         * Fault injection switch — only meaningful on the naive endpoint.
         * NONE                    -> normal happy path
         * CRASH_AFTER_DB_COMMIT   -> DB commits, then the process "crashes"
         *                            before the broker publish call
         * CRASH_AFTER_PUBLISH     -> broker publish succeeds, then the DB
         *                            transaction fails/rolls back
         */
        FailureMode simulateFailure
) {
    public enum FailureMode { NONE, CRASH_AFTER_DB_COMMIT, CRASH_AFTER_PUBLISH }
}
