package org.kucher.socialservice.repository;

import org.kucher.socialservice.model.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing Subscription entities.
 */
public interface ISubscriptionRepository extends JpaRepository<Subscription, UUID> {

    /**
     * Retrieves a page of Subscription entities associated with the given follower UUID.
     *
     * @param uuid     The UUID of the follower.
     * @param pageable The pagination information.
     * @return A page of Subscription entities associated with the given follower UUID.
     */
    Page<Subscription> findAllByFollowerUuid(UUID uuid, Pageable pageable);

    /**
     * Retrieves a page of Subscription entities associated with the given followed user UUID.
     *
     * @param uuid     The UUID of the followed user.
     * @param pageable The pagination information.
     * @return A page of Subscription entities associated with the given followed user UUID.
     */
    Page<Subscription> findAllByFollowedUserUuid(UUID uuid, Pageable pageable);

    /**
     * Retrieves a list of Subscription entities associated with the given follower UUID.
     *
     * @param uuid The UUID of the follower.
     * @return A list of Subscription entities associated with the given follower UUID.
     */
    List<Subscription> findAllByFollowerUuid(UUID uuid);

    /**
     * Retrieves a list of Subscription entities associated with the given followed user UUID.
     *
     * @param uuid The UUID of the followed user.
     * @return A list of Subscription entities associated with the given followed user UUID.
     */
    List<Subscription> findAllByFollowedUserUuid(UUID uuid);

    /**
     * Checks if a Subscription exists between the given follower UUID and followed user UUID.
     *
     * @param followerUuid   The UUID of the follower.
     * @param followedUserUuid The UUID of the followed user.
     * @return True if a Subscription exists, false otherwise.
     */
    boolean existsByFollowerUuidAndFollowedUserUuid(UUID followerUuid, UUID followedUserUuid);
}
