package com.rychkov.eshop.exceptions;

public class OutOfStockException extends Throwable {
    public OutOfStockException(final String message) {
        super(message);
    }
}
