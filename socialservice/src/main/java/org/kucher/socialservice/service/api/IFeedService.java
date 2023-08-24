package org.kucher.socialservice.service.api;

import org.kucher.socialservice.dto.post.ResponsePostDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;
/**
 * Interface for a service handling operations related to reading feed posts.
 */
public interface IFeedService {

    /**
     * Reads a page of feed posts for the specified user.
     *
     * @param uuid The UUID of the user for whom to retrieve feed posts.
     * @param page The page number to retrieve.
     * @param itemsPerPage The number of items to display per page.
     * @param sort The sorting order for the feed posts.
     * @return A Page of ResponsePostDTO objects containing feed post data.
     */
    Page<ResponsePostDTO> read(UUID uuid, int page, int itemsPerPage, int sort);
}
