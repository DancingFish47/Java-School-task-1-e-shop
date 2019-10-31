package com.rychkov.eshop.exceptions;

public class FailedToRepeatOrderException extends RuntimeException {
    public FailedToRepeatOrderException(final String message) {
        super(message);
    }
}
