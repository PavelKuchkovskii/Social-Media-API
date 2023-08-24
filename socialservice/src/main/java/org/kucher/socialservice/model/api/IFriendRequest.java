package org.kucher.socialservice.model.api;

import org.kucher.socialservice.model.enums.EFriendRequestStatus;

import java.util.UUID;

/**
 * Interface representing a friend request entity.
 */
public interface IFriendRequest extends IEssence {

    /**
     * Gets the UUID of the friend request.
     *
     * @return The UUID of the friend request.
     */
    UUID getUuid();

    /**
     * Gets the UUID of the sender user of the friend request.
     *
     * @return The UUID of the sender user.
     */
    UUID getSenderUuid();

    /**
     * Gets the UUID of the receiver user of the friend request.
     *
     * @return The UUID of the receiver user.
     */
    UUID getReceiverUuid();

    /**
     * Gets the status of the friend request.
     *
     * @return The status of the friend request.
     */
    EFriendRequestStatus getStatus();
}
