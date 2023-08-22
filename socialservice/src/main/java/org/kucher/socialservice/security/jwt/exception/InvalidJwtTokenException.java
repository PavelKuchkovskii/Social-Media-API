package org.kucher.socialservice.security.jwt.exception;


import org.kucher.socialservice.security.jwt.exception.api.CustomJwtTokenException;

public class InvalidJwtTokenException extends CustomJwtTokenException {
    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
