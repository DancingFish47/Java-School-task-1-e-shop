package com.rychkov.eshop.exceptions;

public class FailedToDeleteAddressException extends RuntimeException {
    public FailedToDeleteAddressException(String failed_to_delete_address) {
        super(failed_to_delete_address);
    }
}
