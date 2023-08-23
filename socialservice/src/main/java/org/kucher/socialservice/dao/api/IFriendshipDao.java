package org.kucher.socialservice.dao.api;

import org.kucher.socialservice.dao.entity.Friendship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface IFriendshipDao extends JpaRepository<Friendship, UUID> {

    @Query("SELECT f FROM Friendship f WHERE f.user1Uuid = :uuid OR f.user2Uuid = :uuid")
    Page<Friendship> findAllByUserUuid(UUID uuid, Pageable pageable);

    Optional<Friendship> findByUser1UuidAndUser2Uuid(UUID user1, UUID user2);
}
