package org.kucher.socialservice.exception.crud;


import org.kucher.socialservice.exception.crud.api.CrudException;

public class UserAlreadySubscribeException extends CrudException {

    public UserAlreadySubscribeException(String message) {
        super(message);
    }

}