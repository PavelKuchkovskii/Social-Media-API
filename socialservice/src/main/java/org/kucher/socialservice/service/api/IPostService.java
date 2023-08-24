package org.kucher.socialservice.service.api;

import org.kucher.socialservice.model.Post;
import org.kucher.socialservice.dto.post.PostDTO;
import org.kucher.socialservice.dto.post.CreatePostDTO;
import org.kucher.socialservice.dto.post.ResponsePostDTO;
import org.kucher.socialservice.dto.post.UpdatePostDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

/**
 * Interface for a service handling operations related to posts.
 */
public interface IPostService extends IService<CreatePostDTO, UpdatePostDTO, ResponsePostDTO, PostDTO, Post> {

    /**
     * Retrieves a list of posts for the specified user.
     *
     * @param uuid The UUID of the user for whom to retrieve posts.
     * @return A List of ResponsePostDTO objects containing post data.
     */
    List<ResponsePostDTO> get(UUID uuid);

    /**
     * Finds a page of posts for the specified user.
     *
     * @param uuid The UUID of the user for whom to retrieve posts.
     * @param page The page number to retrieve.
     * @param itemsPerPage The number of items to display per page.
     * @return A Page of ResponsePostDTO objects containing post data.
     */
    Page<ResponsePostDTO> findAllByUserUuid(UUID uuid, int page, int itemsPerPage);
}
