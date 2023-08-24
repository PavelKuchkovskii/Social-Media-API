package org.kucher.socialservice.repository.api;

import org.kucher.socialservice.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for managing Post entities.
 */
public interface IPostDao extends MongoRepository<Post, UUID> {

    /**
     * Retrieves a list of top posts associated with the given user UUID, ordered by creation date in descending order.
     *
     * @param uuid The UUID of the user.
     * @return A list of top posts associated with the given user UUID.
     */
    List<Post> findTopPostsByUserUuidOrderByDtCreateDesc(UUID uuid);

    /**
     * Retrieves a page of Post entities associated with the given user UUID.
     *
     * @param uuid     The UUID of the user.
     * @param pageable The pagination information.
     * @return A page of Post entities associated with the given user UUID.
     */
    Page<Post> findAllByUserUuid(UUID uuid, Pageable pageable);
}
