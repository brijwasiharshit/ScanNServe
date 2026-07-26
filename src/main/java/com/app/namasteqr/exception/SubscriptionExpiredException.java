package com.app.namasteqr.exception;

public class SubscriptionExpiredException
        extends RuntimeException {

    public SubscriptionExpiredException(String message) {
        super(message);
    }

}
