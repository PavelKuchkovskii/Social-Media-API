package org.kucher.socialservice.model.api;

import java.util.UUID;

/**
 * Interface representing a subscription entity.
 */
public interface ISubscription {

    /**
     * Gets the UUID of the subscription.
     *
     * @return The UUID of the subscription.
     */
    UUID getUuid();

    /**
     * Gets the UUID of the follower user in the subscription.
     *
     * @return The UUID of the follower user.
     */
    UUID getFollowerUuid();

    /**
     * Gets the UUID of the followed user in the subscription.
     *
     * @return The UUID of the followed user.
     */
    UUID getFollowedUserUuid();

}
