package org.kucher.userservice.exception.crud;

import org.kucher.userservice.exception.crud.api.CrudException;

/**
 * An exception class indicating that a user has already been updated.
 * This exception is a specialized form of CrudException and is used to handle cases where an update operation is attempted
 * on a user, but the user has already been updated since the original operation.
 */
public class UserAlreadyUpdatedException extends CrudException {

    /**
     * Constructs a new UserAlreadyUpdatedException with the specified error message.
     *
     * @param message The detail message describing the exception.
     */
    public UserAlreadyUpdatedException(String message) {
        super(message);
    }
}