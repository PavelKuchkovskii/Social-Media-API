package org.kucher.userservice.security.jwt.exception;

import org.kucher.userservice.security.jwt.exception.api.CustomJwtTokenException;

public class InvalidJwtTokenException extends CustomJwtTokenException {
    public InvalidJwtTokenException(String message) {
        super(message);
    }
}
