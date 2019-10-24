package com.rychkov.eshop.exceptions;

public class FailedToRepeatOrderException extends Throwable {
    public FailedToRepeatOrderException(final String message){
        super(message);
    }
}
