package org.kucher.socialservice.service.api;


import org.kucher.socialservice.model.FriendRequest;
import org.kucher.socialservice.model.Subscription;
import org.kucher.socialservice.dto.friendrequest.FriendRequestDTO;
import org.kucher.socialservice.dto.friendrequest.CreateFriendRequestDTO;
import org.kucher.socialservice.dto.friendrequest.ResponseFriendRequestDTO;
import org.kucher.socialservice.dto.friendrequest.UpdateFriendRequestDTO;
import org.kucher.socialservice.dto.subscription.SubscriptionDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

/**
 * Interface for a service handling operations related to friend requests.
 */
public interface IFriendRequestService extends IService<CreateFriendRequestDTO, UpdateFriendRequestDTO, ResponseFriendRequestDTO, FriendRequestDTO, FriendRequest> {

    /**
     * Reads a page of friend requests for the specified user.
     *
     * @param uuid The UUID of the user for whom to retrieve friend requests.
     * @param page The page number to retrieve.
     * @param itemsPerPage The number of items to display per page.
     * @return A Page of ResponseFriendRequestDTO objects containing friend request data.
     */
    Page<ResponseFriendRequestDTO> read(UUID uuid, int page, int itemsPerPage);
    /**
     * Updates friend request information based on the subscription information.
     *
     * @param subscriptionDTO SubscriptionDTO containing subscription information.
     */
    void updateFromSubscriptionService(SubscriptionDTO subscriptionDTO);

    /**
     * Deletes friend request information based on the subscription.
     *
     * @param subscription Subscription to be used for deletion.
     */
    void deleteFromSubscriptionService(Subscription subscription);
}
