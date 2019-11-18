package com.rychkov.eshop.exceptions;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException(final String message) {
        super(message);
    }
}
