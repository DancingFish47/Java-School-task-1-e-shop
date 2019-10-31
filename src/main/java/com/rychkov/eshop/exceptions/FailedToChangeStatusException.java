package com.rychkov.eshop.exceptions;

public class FailedToChangeStatusException extends RuntimeException {
    public FailedToChangeStatusException(final String message) {
        super(message);
    }
}
