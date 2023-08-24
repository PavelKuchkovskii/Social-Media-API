package org.kucher.socialservice.security.jwt.exception;


import org.kucher.socialservice.security.jwt.exception.api.CustomJwtTokenException;

/**
 * Custom exception class to represent an invalid JWT token.
 * This exception is thrown when a JWT token is found to be invalid or tampered with.
 */
public class InvalidJwtTokenException extends CustomJwtTokenException {

    /**
     * Constructs a new InvalidJwtTokenException with the specified error message.
     *
     * @param message The error message.
     */
    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
