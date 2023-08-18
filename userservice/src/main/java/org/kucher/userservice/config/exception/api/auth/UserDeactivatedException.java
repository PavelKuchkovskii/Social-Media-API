package org.kucher.userservice.config.exception.api.auth;


import org.springframework.security.core.AuthenticationException;

public class UserDeactivatedException extends AuthenticationException {

    public UserDeactivatedException (String message) {
        super(message);
    }
}
