package org.kucher.socialservice.exception.crud;


/**
 * Exception class indicating that a user was not found.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with the specified error message.
     *
     * @param message The error message for the exception.
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}