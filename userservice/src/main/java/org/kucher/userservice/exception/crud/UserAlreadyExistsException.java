package org.kucher.userservice.exception.crud;


import org.kucher.userservice.exception.crud.api.CrudException;

/**
 * An exception class indicating that a user already exists in the system.
 * This exception is a specialized form of CrudException and is used to handle cases where a user is attempted to be created
 * but already exists in the system.
 */
public class UserAlreadyExistsException extends CrudException {

    /**
     * Constructs a new UserAlreadyExistsException with the specified error message.
     *
     * @param message The detail message describing the exception.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}