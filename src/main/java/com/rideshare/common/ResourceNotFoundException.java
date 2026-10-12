package com.rideshare.common;

/**
 * Exception thrown when a requested resource (Ride, Driver, etc.) is not found.
 * Handled by ApiExceptionHandler to return HTTP 404 NOT_FOUND.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
