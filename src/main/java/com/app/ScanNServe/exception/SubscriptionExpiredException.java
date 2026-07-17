package com.app.ScanNServe.exception;

public class SubscriptionExpiredException
        extends RuntimeException {

    public SubscriptionExpiredException(String message) {
        super(message);
    }

}