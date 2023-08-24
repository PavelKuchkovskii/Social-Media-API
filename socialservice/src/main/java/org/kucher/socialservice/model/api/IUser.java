package org.kucher.socialservice.model.api;


import java.util.UUID;

/**
 * Interface representing a user entity.
 */
public interface IUser {

    /**
     * Gets the UUID of the user.
     *
     * @return The UUID of the user.
     */
    UUID getUuid();

    /**
     * Gets the email address of the user.
     *
     * @return The email address of the user.
     */
    String getEmail();

    /**
     * Gets the name of the user.
     *
     * @return The name of the user.
     */
    String getName();

    /**
     * Gets the surname of the user.
     *
     * @return The surname of the user.
     */
    String getSurname();

}
