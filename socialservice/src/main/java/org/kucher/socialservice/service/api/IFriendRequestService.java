package org.kucher.socialservice.service.api;


import org.kucher.socialservice.dao.entity.FriendRequest;
import org.kucher.socialservice.dao.entity.Subscription;
import org.kucher.socialservice.service.dto.friendrequest.FriendRequestDTO;
import org.kucher.socialservice.service.dto.friendrequest.CreateFriendRequestDTO;
import org.kucher.socialservice.service.dto.friendrequest.ResponseFriendRequestDTO;
import org.kucher.socialservice.service.dto.friendrequest.UpdateFriendRequestDTO;
import org.kucher.socialservice.service.dto.subscription.SubscriptionDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IFriendRequestService extends IService<CreateFriendRequestDTO, UpdateFriendRequestDTO, ResponseFriendRequestDTO, FriendRequestDTO, FriendRequest> {

    Page<ResponseFriendRequestDTO> read(UUID uuid, int page, int itemsPerPage);

    void updateFromSubscriptionService(SubscriptionDTO subscriptionDTO);
    void deleteFromSubscriptionService(Subscription subscription);
}
