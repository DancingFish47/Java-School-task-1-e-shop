package com.rychkov.eshop.exceptions;

public class ProcessOrderException extends RuntimeException {
    public ProcessOrderException(final String message) {
        super(message);
    }
}
