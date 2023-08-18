package org.kucher.userservice.security.jwt.exception.api;

import io.jsonwebtoken.JwtException;

public class CustomJwtTokenException extends JwtException {
    public CustomJwtTokenException(String message) {
        super(message);
    }
}
