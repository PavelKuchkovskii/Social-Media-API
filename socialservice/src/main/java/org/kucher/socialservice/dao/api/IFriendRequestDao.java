package org.kucher.socialservice.dao.api;

import org.kucher.socialservice.dao.entity.FriendRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IFriendRequestDao extends JpaRepository<FriendRequest, UUID> {
}
