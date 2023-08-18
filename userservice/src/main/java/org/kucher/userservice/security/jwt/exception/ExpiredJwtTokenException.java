package org.kucher.userservice.security.jwt.exception;

import org.kucher.userservice.security.jwt.exception.api.CustomJwtTokenException;

public class ExpiredJwtTokenException extends CustomJwtTokenException {
    public ExpiredJwtTokenException(String message) {
        super(message);
    }
}
