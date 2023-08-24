package org.kucher.socialservice.service.api;

import org.kucher.socialservice.dto.friendhip.FriendshipDTO;
import org.kucher.socialservice.dto.friendhip.ResponseFriendshipDTO;
import org.kucher.socialservice.model.FriendRequest;
import org.kucher.socialservice.model.Friendship;
import org.kucher.socialservice.model.Subscription;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Interface for a service handling operations related to friendships.
 */
public interface IFriendshipService {

    /**
     * Creates a new friendship based on a friend request.
     *
     * @param friendRequest The friend request used to create the friendship.
     * @return true if the friendship creation was successful, otherwise false.
     */
    boolean create(FriendRequest friendRequest);

    /**
     * Reads a page of friendships for the specified user.
     *
     * @param uuid The UUID of the user for whom to retrieve friendships.
     * @param page The page number to retrieve.
     * @param itemsPerPage The number of items to display per page.
     * @return A Page of ResponseFriendshipDTO objects containing friendship data.
     */
    Page<ResponseFriendshipDTO> read(UUID uuid, int page, int itemsPerPage);

    /**
     * Deletes a friendship based on the subscription.
     *
     * @param subscription Subscription containing information about the friendship to delete.
     */
    void delete(Subscription subscription);

    /**
     * Validates the data in the FriendshipDTO.
     *
     * @param dto FriendshipDTO to be validated.
     * @return true if the FriendshipDTO is valid, otherwise false.
     */
    boolean validate(FriendshipDTO dto);

    /**
     * Converts a FriendshipDTO to a Friendship entity.
     *
     * @param dto FriendshipDTO to be converted.
     * @return Friendship entity based on the FriendshipDTO.
     */
    Friendship mapToEntity(FriendshipDTO dto);

    /**
     * Converts a Friendship entity to a ResponseFriendshipDTO.
     *
     * @param entity Friendship entity to be converted.
     * @return ResponseFriendshipDTO containing friendship data.
     */
    ResponseFriendshipDTO mapToDTO(Friendship entity);
}
