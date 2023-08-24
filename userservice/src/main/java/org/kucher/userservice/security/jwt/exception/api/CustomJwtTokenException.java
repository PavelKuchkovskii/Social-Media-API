package org.kucher.userservice.security.jwt.exception.api;

import io.jsonwebtoken.JwtException;

/**
 * Custom exception class to represent errors related to JWT token processing.
 */
public class CustomJwtTokenException extends JwtException {

    /**
     * Constructs a new CustomJwtTokenException with the specified error message.
     *
     * @param message The error message.
     */
    public CustomJwtTokenException(String message) {
        super(message);
    }
}
