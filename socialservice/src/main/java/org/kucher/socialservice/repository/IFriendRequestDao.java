package org.kucher.socialservice.repository;

import org.kucher.socialservice.model.FriendRequest;
import org.kucher.socialservice.model.enums.EFriendRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing FriendRequest entities.
 */
public interface IFriendRequestDao extends JpaRepository<FriendRequest, UUID> {

    /**
     * Retrieves a page of FriendRequest entities with the given receiver UUID and status.
     *
     * @param uuid   The UUID of the receiver.
     * @param status The status of the friend requests to retrieve.
     * @param pageable The pagination information.
     * @return A page of FriendRequest entities with the given receiver UUID and status.
     */
    Page<FriendRequest> findAllByReceiverUuidAndStatus(UUID uuid, EFriendRequestStatus status, Pageable pageable);

    /**
     * Retrieves a FriendRequest entity based on the sender UUID and receiver UUID.
     *
     * @param senderUuid   The UUID of the sender.
     * @param receiverUuid The UUID of the receiver.
     * @return An Optional containing the retrieved FriendRequest entity, if found.
     */
    Optional<FriendRequest> findBySenderUuidAndReceiverUuid(UUID senderUuid, UUID receiverUuid);
}
