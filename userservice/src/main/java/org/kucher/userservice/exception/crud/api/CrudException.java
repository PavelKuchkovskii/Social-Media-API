package org.kucher.userservice.exception.crud.api;

/**
 * A custom runtime exception class used for CRUD (Create, Read, Update, Delete) operation related exceptions.
 * This exception is used to indicate errors or issues related to CRUD operations.
 */
public class CrudException extends RuntimeException {

    /**
     * Constructs a new CrudException with the specified error message.
     *
     * @param message The detail message describing the exception.
     */
    public CrudException(String message) {
        super(message);
    }
}
