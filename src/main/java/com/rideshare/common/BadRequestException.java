package com.rideshare.common;

/**
 * Exception thrown when a client request violates business validation rules.
 * Examples: ride already cancelled, driver already assigned, invalid status transition.
 * Handled by ApiExceptionHandler to return HTTP 400 BAD_REQUEST.
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
