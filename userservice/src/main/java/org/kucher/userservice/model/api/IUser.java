package org.kucher.userservice.model.api;


import org.kucher.userservice.model.enums.EUserRole;
import org.kucher.userservice.model.enums.EUserStatus;

/**
 * This interface represents a user entity with essential properties.
 * It extends the {@link IEssence} interface, providing common properties for entities.
 */
public interface IUser extends IEssence {

    /**
     * Returns the email address associated with the user.
     *
     * @return The email address of the user.
     */
    String getEmail();

    /**
     * Returns the name of the user.
     *
     * @return The name of the user.
     */
    String getName();

    /**
     * Returns the surname of the user.
     *
     * @return The surname of the user.
     */
    String getSurname();

    /**
     * Returns the hashed password of the user.
     *
     * @return The hashed password of the user.
     */
    String getPassword();

    /**
     * Returns the role of the user.
     *
     * @return The role of the user.
     */
    EUserRole getRole();

    /**
     * Returns the status of the user.
     *
     * @return The status of the user.
     */
    EUserStatus getStatus();
}
