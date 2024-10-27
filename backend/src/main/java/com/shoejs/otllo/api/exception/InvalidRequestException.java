package com.shoejs.otllo.api.exception;

/**
 * Exception to be thrown when a user makes an invalid request
 */
public class InvalidRequestException extends RuntimeException {

    public InvalidRequestException(String message) {
        super(message);
    }
}
