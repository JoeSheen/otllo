package com.shoejs.otllo.api.exception;

/**
 * Exception to be thrown when a resource could not be found
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
