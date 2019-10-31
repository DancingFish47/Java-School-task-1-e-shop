package com.rychkov.eshop.exceptions;

public class ReturnBooksToStockException extends RuntimeException {
    public ReturnBooksToStockException(final String message) {
        super(message);
    }
}
