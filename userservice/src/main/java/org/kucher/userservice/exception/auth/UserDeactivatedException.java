package org.kucher.userservice.exception.auth;


import org.springframework.security.core.AuthenticationException;

/**
 * An exception to indicate that a user account is deactivated.
 * This exception is typically thrown when attempting to perform operations that require an active account.
 */
public class UserDeactivatedException extends AuthenticationException {

    /**
     * Constructs a new UserDeactivatedException with the specified error message.
     *
     * @param message The detail message describing the exception.
     */
    public UserDeactivatedException(String message) {
        super(message);
    }
}
