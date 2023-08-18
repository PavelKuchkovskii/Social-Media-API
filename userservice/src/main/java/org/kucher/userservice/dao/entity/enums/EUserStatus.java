package org.kucher.userservice.dao.entity.enums;

public enum EUserStatus {

    //Waiting to send message to email
    WAITING_VERIFICATION,

    //Waiting to confirm email
    WAITING_CONFIRM,

    //Waiting to confirm by ADMIN
    WAITING_ACTIVATION,

    //ACTIVATED
    ACTIVATED,

    //DEACTIVATED
    DEACTIVATED;

    public static EUserStatus get(String name) {

        try {
            return EUserStatus.valueOf(name);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid status");
        }
    }
}