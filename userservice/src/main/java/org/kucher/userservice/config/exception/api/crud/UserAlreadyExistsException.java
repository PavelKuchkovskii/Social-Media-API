package org.kucher.userservice.config.exception.api.crud;


import org.kucher.userservice.config.exception.api.crud.api.CrudException;

public class UserAlreadyExistsException extends CrudException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}