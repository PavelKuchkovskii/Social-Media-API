package org.kucher.socialservice.service.api;

import org.kucher.socialservice.dto.subscription.CreateSubscriptionDTO;
import org.kucher.socialservice.dto.subscription.ResponseSubscriptionDTO;
import org.kucher.socialservice.dto.subscription.SubscriptionDTO;
import org.kucher.socialservice.model.FriendRequest;
import org.kucher.socialservice.model.Subscription;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

/**
 * Interface for a service handling operations related to subscriptions.
 */
public interface ISubscriptionService {

    /**
     * Creates a new subscription based on the provided Data Transfer Object.
     *
     * @param dto Data Transfer Object containing subscription data.
     * @return Data Transfer Object with data of the created subscription.
     */
    ResponseSubscriptionDTO create(CreateSubscriptionDTO dto);

    /**
     * Creates a subscription based on a friend request.
     *
     * @param friendRequest The friend request used to create the subscription.
     */
    void createFromFriendRequestService(FriendRequest friendRequest);

    /**
     * Reads a page of subscriptions for the specified user.
     *
     * @param uuid The UUID of the user for whom to retrieve subscriptions.
     * @param isFollowers Flag indicating whether to retrieve followers or followed users.
     * @param page The page number to retrieve.
     * @param itemsPerPage The number of items to display per page.
     * @return A Page of ResponseSubscriptionDTO objects containing subscription data.
     */
    Page<ResponseSubscriptionDTO> read(UUID uuid, boolean isFollowers, int page, int itemsPerPage);

    /**
     * Retrieves a list of subscriptions for the specified user.
     *
     * @param uuid The UUID of the user for whom to retrieve subscriptions.
     * @param isFollowers Flag indicating whether to retrieve followers or followed users.
     * @return A List of ResponseSubscriptionDTO objects containing subscription data.
     */
    List<ResponseSubscriptionDTO> read(UUID uuid, boolean isFollowers);

    /**
     * Deletes a subscription based on the provided identifier.
     *
     * @param uuid The UUID of the subscription to be deleted.
     * @return true if the deletion was successful, otherwise false.
     */
    boolean delete(UUID uuid);

    /**
     * Validates the data in the SubscriptionDTO.
     *
     * @param dto SubscriptionDTO to be validated.
     * @return true if the SubscriptionDTO is valid, otherwise false.
     */
    boolean validate(SubscriptionDTO dto);

    /**
     * Converts a SubscriptionDTO to a Subscription entity.
     *
     * @param dto SubscriptionDTO to be converted.
     * @return Subscription entity based on the SubscriptionDTO.
     */
    Subscription mapToEntity(SubscriptionDTO dto);

    /**
     * Converts a Subscription entity to a ResponseSubscriptionDTO.
     *
     * @param entity Subscription entity to be converted.
     * @return ResponseSubscriptionDTO containing subscription data.
     */
    ResponseSubscriptionDTO mapToDTO(Subscription entity);
}
