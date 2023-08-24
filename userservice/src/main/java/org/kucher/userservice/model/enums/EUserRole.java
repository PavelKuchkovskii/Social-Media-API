package org.kucher.userservice.model.enums;

/**
 * Enum representing different roles that a user can have in the application.
 */
public enum EUserRole {

    /**
     * Role representing an administrator.
     */
    ROLE_ADMIN,

    /**
     * Role representing a regular user.
     */
    ROLE_USER;

    /**
     * Converts a string role name to its corresponding enum value.
     *
     * @param name The string representation of the role.
     * @return The corresponding {@link EUserRole} enum value.
     * @throws IllegalArgumentException If the provided name does not match any role.
     */
    public static EUserRole get(String name) {
        try {
            return EUserRole.valueOf(name);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid role");
        }
    }
}