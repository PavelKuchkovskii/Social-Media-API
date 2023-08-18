package org.kucher.userservice.config.exception.api.registration;


import org.kucher.userservice.config.exception.api.registration.api.RegistrationException;

public class UserAlreadyActivatedException extends RegistrationException {
    public UserAlreadyActivatedException(String message) {
        super(message);
    }
}