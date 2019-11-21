package com.rychkov.eshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Out of stock")
public class OutOfStockException extends RuntimeException {
    public OutOfStockException(final String message) {
        super(message);
    }
}
