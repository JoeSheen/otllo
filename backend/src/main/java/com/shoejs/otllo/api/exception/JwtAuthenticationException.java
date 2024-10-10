package com.shoejs.otllo.api.exception;

/**
 * Exception thrown during authentication filter execution
 */
public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException(String message) {
        super(message);
    }
}
