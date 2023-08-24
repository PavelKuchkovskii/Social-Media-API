package org.kucher.userservice.model.enums;

/**
 * Enum representing different statuses that a user can have in the application.
 */
public enum EUserStatus {

    /**
     * Status indicating that the user is waiting to send a verification message to their email.
     */
    WAITING_VERIFICATION,

    /**
     * Status indicating that the user is waiting to confirm their email.
     */
    WAITING_CONFIRM,

    /**
     * Status indicating that the user is waiting for activation by an administrator.
     */
    WAITING_ACTIVATION,

    /**
     * Status indicating that the user's account is activated.
     */
    ACTIVATED,

    /**
     * Status indicating that the user's account is deactivated.
     */
    DEACTIVATED;

    /**
     * Converts a string status name to its corresponding enum value.
     *
     * @param name The string representation of the status.
     * @return The corresponding {@link EUserStatus} enum value.
     * @throws IllegalArgumentException If the provided name does not match any status.
     */
    public static EUserStatus get(String name) {
        try {
            return EUserStatus.valueOf(name);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid status");
        }
    }
}