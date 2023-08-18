package org.kucher.userservice.config.exception.api.auth;


import org.springframework.security.core.AuthenticationException;

public class NotActivatedException extends AuthenticationException {

    public NotActivatedException(String message) {
        super(message);
    }
}
