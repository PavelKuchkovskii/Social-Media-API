package org.kucher.socialservice.repository;

import org.kucher.socialservice.model.Friendship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Friendship entities.
 */
public interface IFriendshipDao extends JpaRepository<Friendship, UUID> {

    /**
     * Retrieves a page of Friendship entities associated with the given user UUID.
     *
     * @param uuid     The UUID of the user.
     * @param pageable The pagination information.
     * @return A page of Friendship entities associated with the given user UUID.
     */
    @Query("SELECT f FROM Friendship f WHERE f.user1Uuid = :uuid OR f.user2Uuid = :uuid")
    Page<Friendship> findAllByUserUuid(UUID uuid, Pageable pageable);

    /**
     * Retrieves a Friendship entity based on the two user UUIDs.
     *
     * @param user1 The UUID of the first user.
     * @param user2 The UUID of the second user.
     * @return An Optional containing the retrieved Friendship entity, if found.
     */
    Optional<Friendship> findByUser1UuidAndUser2Uuid(UUID user1, UUID user2);
}
