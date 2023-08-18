package org.kucher.userservice.config.exception.api.crud;

import org.kucher.userservice.config.exception.api.crud.api.CrudException;

public class UserAlreadyUpdatedException extends CrudException {

    public UserAlreadyUpdatedException(String message) {
        super(message);
    }

}