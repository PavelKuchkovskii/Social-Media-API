package org.kucher.socialservice.exception.rest;

/**
 * Exception class indicating a generic error in a REST API operation.
 */
public class RestApiException extends RuntimeException {

    /**
     * Constructs a new RestApiException with the specified error message.
     *
     * @param message The error message for the exception.
     */
    public RestApiException(String message) {
        super(message);
    }
}
