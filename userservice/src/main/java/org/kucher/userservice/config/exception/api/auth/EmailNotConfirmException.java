package org.kucher.userservice.config.exception.api.auth;


import org.springframework.security.core.AuthenticationException;

public class EmailNotConfirmException extends AuthenticationException {

    public EmailNotConfirmException(String message) {
        super(message);
    }
}
