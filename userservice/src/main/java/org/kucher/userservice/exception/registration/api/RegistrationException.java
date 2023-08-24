package org.kucher.userservice.exception.registration.api;

/**
 * An exception class indicating an error during user registration.
 * This exception is a specialized form of RuntimeException and is used to handle errors that occur during the registration process.
 */
public class RegistrationException extends RuntimeException {

    /**
     * Constructs a new RegistrationException with the specified error message.
     *
     * @param message The detail message describing the exception.
     */
    public RegistrationException(String message) {
        super(message);
    }
}
