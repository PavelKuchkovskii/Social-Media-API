package org.kucher.userservice.security.jwt.exception;

import org.kucher.userservice.security.jwt.exception.api.CustomJwtTokenException;

/**
 * Custom exception class to represent an expired JWT token.
 * This exception is thrown when a JWT token has expired and is no longer valid.
 */
public class ExpiredJwtTokenException extends CustomJwtTokenException {

    /**
     * Constructs a new ExpiredJwtTokenException with the specified error message.
     *
     * @param message The error message.
     */
    public ExpiredJwtTokenException(String message) {
        super(message);
    }
}
