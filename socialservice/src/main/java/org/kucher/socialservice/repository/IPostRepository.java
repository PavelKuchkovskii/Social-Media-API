package org.kucher.socialservice.repository;

import org.kucher.socialservice.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing Post entities.
 */
public interface IPostRepository extends MongoRepository<Post, UUID> {

    /**
     * Retrieves a list of posts associated with a specific user's UUID.
     *
     * @param uuid The UUID of the user whose posts are being retrieved.
     * @return A list of posts belonging to the specified user, ordered by creation date in descending order.
     */
    List<Post> findAllByUserUuid(UUID uuid);

    /**
     * Retrieves a page of Post entities associated with the given user UUID.
     *
     * @param uuid     The UUID of the user.
     * @param pageable The pagination information.
     * @return A page of Post entities associated with the given user UUID.
     */
    Page<Post> findAllByUserUuid(UUID uuid, Pageable pageable);
}
