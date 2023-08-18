package org.kucher.userservice.dao.entity.enums;

public enum EUserRole {

    ROLE_ADMIN,
    ROLE_USER;

    public static EUserRole get(String name) {

        try {
            return EUserRole.valueOf(name);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid role");
        }
    }
}