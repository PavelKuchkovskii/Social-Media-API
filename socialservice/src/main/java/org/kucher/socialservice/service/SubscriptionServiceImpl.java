package org.kucher.socialservice.service;

import org.kucher.socialservice.event.MutualSubscriptionEvent;
import org.kucher.socialservice.exception.crud.UserAlreadySubscribeException;
import org.kucher.socialservice.repository.ISubscriptionDao;
import org.kucher.socialservice.model.FriendRequest;
import org.kucher.socialservice.model.Subscription;
import org.kucher.socialservice.model.User;
import org.kucher.socialservice.model.builder.SubscriptionBuilder;
import org.kucher.socialservice.dto.friendrequest.CreateFriendRequestDTO;
import org.kucher.socialservice.dto.subscription.CreateSubscriptionDTO;
import org.kucher.socialservice.dto.subscription.ResponseSubscriptionDTO;
import org.kucher.socialservice.dto.subscription.SubscriptionDTO;
import org.kucher.socialservice.service.api.IFriendRequestService;
import org.kucher.socialservice.service.api.IFriendshipService;
import org.kucher.socialservice.service.api.ISubscriptionService;
import org.kucher.socialservice.service.api.IUserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 *
 * Implementation of the ISubscriptionService interface for managing subscriptions.
 */
@Service
public class SubscriptionServiceImpl implements ISubscriptionService {

    private final ISubscriptionDao dao;
    private final IFriendRequestService friendRequestService;
    private final IFriendshipService friendshipService;
    private final IUserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public SubscriptionServiceImpl(ISubscriptionDao dao, IFriendRequestService friendRequestService, IFriendshipService friendshipService, IUserService userService, ApplicationEventPublisher eventPublisher) {
        this.dao = dao;
        this.friendRequestService = friendRequestService;
        this.friendshipService = friendshipService;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Creates a subscription to a user based on the provided data.
     *
     * @param dto Data for creating the subscription.
     * @return DTO containing information about the created subscription.
     * @throws IllegalArgumentException if attempting to subscribe to oneself.
     * @throws UserAlreadySubscribeException if the user is already subscribed to the specified user.
     * @throws RuntimeException if something goes wrong during the operation.
     */
    @Override
    @Transactional
    public ResponseSubscriptionDTO create(CreateSubscriptionDTO dto) {

        UUID follower = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        if(!follower.equals(dto.getFollowedUserUuid())) {
            User user = userService.getUserByUuid(dto.getFollowedUserUuid());

            if(!dao.existsByFollowerUuidAndFollowedUserUuid(follower, user.getUuid())) {
                SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
                subscriptionDTO.setUuid(UUID.randomUUID());
                subscriptionDTO.setFollowerUuid(follower);
                subscriptionDTO.setFollowedUserUuid(user.getUuid());

                if (validate(subscriptionDTO)) {

                    if(!dao.existsByFollowerUuidAndFollowedUserUuid(user.getUuid(), follower)) {
                        //Create friendRequest
                        friendRequestService.create(new CreateFriendRequestDTO(subscriptionDTO.getFollowedUserUuid()));
                    }
                    else {
                        //Create friendship
                        eventPublisher.publishEvent(new MutualSubscriptionEvent(subscriptionDTO));
                        friendRequestService.updateFromSubscriptionService(subscriptionDTO);
                    }

                    Subscription subscription = mapToEntity(subscriptionDTO);
                    dao.save(subscription);

                    return mapToDTO(subscription);
                } else {
                    throw new RuntimeException("Something was wrong. Try again later");
                }
            }
            else {
                throw new UserAlreadySubscribeException("Already subscribe");
            }
        }
        else {
            throw new IllegalArgumentException("Unable to perform operation");
        }
    }

    /**
     * Creates a subscription from a friend request.
     *
     * @param friendRequest The friend request from which to create the subscription.
     * @throws RuntimeException if something goes wrong during the operation.
     */
    @Override
    @Transactional
    public void createFromFriendRequestService(FriendRequest friendRequest) {

        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setUuid(UUID.randomUUID());
        subscriptionDTO.setFollowerUuid(friendRequest.getReceiverUuid());
        subscriptionDTO.setFollowedUserUuid(friendRequest.getSenderUuid());

        if (validate(subscriptionDTO)) {
            //Create friendship
            eventPublisher.publishEvent(new MutualSubscriptionEvent(subscriptionDTO));

            Subscription subscription = mapToEntity(subscriptionDTO);
            dao.save(subscription);

            mapToDTO(subscription);
        }
        else {
            throw new RuntimeException("Something was wrong. Try again later");
        }

    }


    /**
     * Retrieves a page of subscription information based on the provided parameters.
     *
     * @param uuid The UUID of the user for whom to retrieve subscriptions.
     * @param isFollowers Whether to retrieve subscriptions for followers (true) or followed users (false).
     * @param page The page number (0-indexed) of the result set.
     * @param itemsPerPage The number of items per page.
     * @return A page of DTOs containing subscription information.
     */
    @Override
    public Page<ResponseSubscriptionDTO> read(UUID uuid, boolean isFollowers, int page, int itemsPerPage) {
        Pageable pageable = PageRequest.of(page, itemsPerPage);

        Page<Subscription> subscriptions;

        if (isFollowers) {
            subscriptions = dao.findAllByFollowedUserUuid(uuid, pageable);
        } else {
            subscriptions = dao.findAllByFollowerUuid(uuid, pageable);
        }

        return new PageImpl<>(subscriptions.get().map(this::mapToDTO)
                .collect(Collectors.toList()), pageable, subscriptions.getTotalElements());
    }

    /**
     * Retrieves a list of subscription information based on the provided parameters.
     *
     * @param uuid The UUID of the user for whom to retrieve subscriptions.
     * @param isFollowers Whether to retrieve subscriptions for followers (true) or followed users (false).
     * @return A list of DTOs containing subscription information.
     */
    @Override
    public List<ResponseSubscriptionDTO> read(UUID uuid, boolean isFollowers) {

        List<Subscription> subscriptions;

        if (isFollowers) {
            subscriptions = dao.findAllByFollowedUserUuid(uuid);
        } else {
            subscriptions = dao.findAllByFollowerUuid(uuid);
        }

        return subscriptions.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Deletes a subscription based on the provided UUID.
     *
     * @param uuid The UUID of the subscription to be deleted.
     * @return `true` if the subscription was successfully deleted, `false` otherwise.
     * @throws AccessDeniedException if the authenticated user does not have permission to delete the subscription.
     * @throws RuntimeException if the subscription is not found.
     */
    @Override
    @Transactional
    public boolean delete(UUID uuid) {

        Optional<Subscription> optional = dao.findById(uuid);

        if(optional.isPresent()) {
            UUID muuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

            if(muuid.equals(optional.get().getFollowerUuid())) {

                //Delete friendship
                friendshipService.delete(optional.get());

                //Delete friendRequest
                friendRequestService.deleteFromSubscriptionService(optional.get());
                dao.deleteById(uuid);
                return true;
            }
            else {
                throw new AccessDeniedException("Access denied");
            }
        }
        else {
            throw new RuntimeException("Post not found");
        }
    }


    @Override
    public boolean validate(SubscriptionDTO dto) {
        return true;
    }
    @Override
    public Subscription mapToEntity(SubscriptionDTO dto) {
        return SubscriptionBuilder
                .create()
                .setUuid(dto.getUuid())
                .setFollower(dto.getFollowerUuid())
                .setFollowedUser(dto.getFollowedUserUuid())
                .build();
    }

    @Override
    public ResponseSubscriptionDTO mapToDTO(Subscription entity) {
        ResponseSubscriptionDTO responseFriendshipDTO = new ResponseSubscriptionDTO();
        responseFriendshipDTO.setUuid(entity.getUuid());
        responseFriendshipDTO.setFollower(userService.getUserByUuid(entity.getFollowerUuid()));
        responseFriendshipDTO.setFollowedUser(userService.getUserByUuid(entity.getFollowedUserUuid()));

        return responseFriendshipDTO;
    }
}
