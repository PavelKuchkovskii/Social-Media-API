package org.kucher.socialservice.exception.crud;


/**
 * Exception class indicating that a user has already subscribed to another user.
 */
public class UserAlreadySubscribeException extends RuntimeException {

    /**
     * Constructs a new UserAlreadySubscribeException with the specified error message.
     *
     * @param message The error message for the exception.
     */
    public UserAlreadySubscribeException(String message) {
        super(message);
    }
}