package org.kucher.socialservice.service;

import org.kucher.socialservice.config.event.MutualSubscriptionEvent;
import org.kucher.socialservice.config.exception.api.crud.UserAlreadySubscribeException;
import org.kucher.socialservice.dao.api.ISubscriptionDao;
import org.kucher.socialservice.dao.entity.FriendRequest;
import org.kucher.socialservice.dao.entity.Subscription;
import org.kucher.socialservice.dao.entity.User;
import org.kucher.socialservice.dao.entity.builder.SubscriptionBuilder;
import org.kucher.socialservice.service.dto.friendrequest.CreateFriendRequestDTO;
import org.kucher.socialservice.service.dto.subscription.CreateSubscriptionDTO;
import org.kucher.socialservice.service.dto.subscription.ResponseSubscriptionDTO;
import org.kucher.socialservice.service.dto.subscription.SubscriptionDTO;
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

@Service
public class SubscriptionService {

    private final ISubscriptionDao dao;
    private final FriendRequestService friendRequestService;
    private final FriendshipService friendshipService;
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public SubscriptionService(ISubscriptionDao dao, FriendRequestService friendRequestService, FriendshipService friendshipService, UserService userService, ApplicationEventPublisher eventPublisher) {
        this.dao = dao;
        this.friendRequestService = friendRequestService;
        this.friendshipService = friendshipService;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

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


    //true - my followers
    //false - followed
    public Page<ResponseSubscriptionDTO> read(UUID uuid, boolean b, int page, int itemsPerPage) {

        Pageable pageable = PageRequest.of(page, itemsPerPage);

        Page<Subscription> subscriptions;

        if (b) {
            subscriptions = dao.findAllByFollowedUserUuid(uuid, pageable);
        } else {
            subscriptions = dao.findAllByFollowerUuid(uuid, pageable);
        }

        return new PageImpl<>(subscriptions.get().map(this::mapToDTO)
                .collect(Collectors.toList()), pageable, subscriptions.getTotalElements());
    }

    //true - my followers
    //false - followed
    public List<ResponseSubscriptionDTO> read(UUID uuid, boolean b) {

        List<Subscription> subscriptions;

        if (b) {
            subscriptions = dao.findAllByFollowedUserUuid(uuid);
        } else {
            subscriptions = dao.findAllByFollowerUuid(uuid);
        }

        return subscriptions.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

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

    public boolean validate(SubscriptionDTO dto) {
        return true;
    }

    public Subscription mapToEntity(SubscriptionDTO dto) {
        return SubscriptionBuilder
                .create()
                .setUuid(dto.getUuid())
                .setFollower(dto.getFollowerUuid())
                .setFollowedUser(dto.getFollowedUserUuid())
                .build();
    }

    public ResponseSubscriptionDTO mapToDTO(Subscription entity) {
        ResponseSubscriptionDTO responseFriendshipDTO = new ResponseSubscriptionDTO();
        responseFriendshipDTO.setUuid(entity.getUuid());
        responseFriendshipDTO.setFollower(userService.getUserByUuid(entity.getFollowerUuid()));
        responseFriendshipDTO.setFollowedUser(userService.getUserByUuid(entity.getFollowedUserUuid()));

        return responseFriendshipDTO;
    }
}
