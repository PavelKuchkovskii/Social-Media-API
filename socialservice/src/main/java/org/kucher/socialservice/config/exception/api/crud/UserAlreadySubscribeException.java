package org.kucher.socialservice.config.exception.api.crud;


import org.kucher.socialservice.config.exception.api.crud.api.CrudException;

public class UserAlreadySubscribeException extends CrudException {

    public UserAlreadySubscribeException(String message) {
        super(message);
    }

}