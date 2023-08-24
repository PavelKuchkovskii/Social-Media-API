package org.kucher.socialservice.exception.crud;


import org.kucher.socialservice.exception.crud.api.CrudException;

public class UserNotFoundException extends CrudException {

    public UserNotFoundException(String message) {
        super(message);
    }

}