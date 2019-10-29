package com.rychkov.eshop.exceptions;

public class FailedToChangeStatusException extends Throwable {
    public FailedToChangeStatusException(final String message) {
        super(message);
    }
}
