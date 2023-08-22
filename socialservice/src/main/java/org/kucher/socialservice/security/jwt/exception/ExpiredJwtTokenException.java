package org.kucher.socialservice.security.jwt.exception;


import org.kucher.socialservice.security.jwt.exception.api.CustomJwtTokenException;

public class ExpiredJwtTokenException extends CustomJwtTokenException {
    public ExpiredJwtTokenException(String message) {
        super(message);
    }
}
