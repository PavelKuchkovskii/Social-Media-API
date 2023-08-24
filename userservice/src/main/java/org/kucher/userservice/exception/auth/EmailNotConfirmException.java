package org.kucher.userservice.exception.auth;


import org.springframework.security.core.AuthenticationException;


/**
 * Exception thrown when a user's email is not confirmed during authentication.
 * This exception extends {@link org.springframework.security.core.AuthenticationException}.
 */
public class EmailNotConfirmException extends AuthenticationException {


    /**
     * Constructs a new EmailNotConfirmException with the specified error message.
     *
     * @param message The detail message describing the exception.
     */
    public EmailNotConfirmException(String message) {
        super(message);
    }
}
