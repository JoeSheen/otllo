package com.shoejs.otllo.api.exception;

public class DuplicateEntityException extends IllegalArgumentException {

    public DuplicateEntityException(String message) {
        super(message);
    }
}
