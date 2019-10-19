package com.rychkov.eshop.exceptions;

public class PasswordMismatchException extends Throwable {
    public PasswordMismatchException(final String message) {
        super(message);
    }
}
