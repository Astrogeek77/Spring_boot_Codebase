package com.dualwrite.demo.naive;

/** Thrown to simulate a mid-flight crash between the DB write and the broker publish. */
public class DualWriteFailureException extends RuntimeException {
    public DualWriteFailureException(String message) {
        super(message);
    }
}
