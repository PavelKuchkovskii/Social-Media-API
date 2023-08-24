package org.kucher.userservice.exception.registration;


import org.kucher.userservice.exception.registration.api.RegistrationException;

/**
 * An exception class indicating that a user is already activated.
 * This exception is a specialized form of RegistrationException and is used when trying to activate a user account that is already activated.
 */
public class UserAlreadyActivatedException extends RegistrationException {

    /**
     * Constructs a new UserAlreadyActivatedException with the specified error message.
     *
     * @param message The detail message describing the exception.
     */
    public UserAlreadyActivatedException(String message) {
        super(message);
    }
}