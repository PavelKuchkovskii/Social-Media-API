package org.kucher.socialservice.model.api;

import java.util.UUID;

/**
 * Interface representing a friendship entity.
 */
public interface IFriendship {

    /**
     * Gets the UUID of the friendship.
     *
     * @return The UUID of the friendship.
     */
    UUID getUuid();

    /**
     * Gets the UUID of the first user in the friendship.
     *
     * @return The UUID of the first user.
     */
    UUID getUser1Uuid();

    /**
     * Gets the UUID of the second user in the friendship.
     *
     * @return The UUID of the second user.
     */
    UUID getUser2Uuid();
}
