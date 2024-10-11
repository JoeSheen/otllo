package com.shoejs.otllo.api.exception;

/**
 * Exception to be thrown when a duplicate entity is encountered
 */
public class DuplicateEntityException extends IllegalArgumentException {

    public DuplicateEntityException(String message) {
        super(message);
    }
}
