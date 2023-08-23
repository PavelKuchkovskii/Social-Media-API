package org.kucher.socialservice.dao.api;

import org.kucher.socialservice.dao.entity.FriendRequest;
import org.kucher.socialservice.dao.entity.enums.EFriendRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IFriendRequestDao extends JpaRepository<FriendRequest, UUID> {

    Page<FriendRequest> findAllByReceiverUuidAndStatus(UUID uuid, EFriendRequestStatus status, Pageable pageable);

    Optional<FriendRequest> findBySenderUuidAndReceiverUuid(UUID senderUuid, UUID receiverUuid);
}
