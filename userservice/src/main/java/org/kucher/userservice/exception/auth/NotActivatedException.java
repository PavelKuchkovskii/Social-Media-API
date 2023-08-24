package org.kucher.userservice.exception.auth;


import org.springframework.security.core.AuthenticationException;

/**
 * An exception to indicate that a user account is not activated.
 * This exception is typically thrown when attempting to perform operations that require an activated account.
 */
public class NotActivatedException extends AuthenticationException {

    /**
     * Constructs a new NotActivatedException with the specified error message.
     *
     * @param message The detail message describing the exception.
     */
    public NotActivatedException(String message) {
        super(message);
    }
}
