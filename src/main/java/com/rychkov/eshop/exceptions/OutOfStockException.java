package com.rychkov.eshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class OutOfStockException extends Throwable {
    public OutOfStockException(final String message) {
        super(message);
    }
}
