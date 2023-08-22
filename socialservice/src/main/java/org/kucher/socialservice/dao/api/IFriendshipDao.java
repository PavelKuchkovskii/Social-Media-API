package org.kucher.socialservice.dao.api;

import org.kucher.socialservice.dao.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IFriendshipDao extends JpaRepository<Friendship, UUID> {
}
