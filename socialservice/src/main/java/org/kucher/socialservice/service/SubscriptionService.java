package org.kucher.socialservice.service;

import org.kucher.socialservice.dao.api.ISubscriptionDao;
import org.kucher.socialservice.dao.entity.Subscription;
import org.kucher.socialservice.dao.entity.builder.SubscriptionBuilder;
import org.kucher.socialservice.service.dto.subscription.ResponseSubscriptionDTO;
import org.kucher.socialservice.service.dto.subscription.SubscriptionDTO;
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
    private final UserService userService;

    public SubscriptionService(ISubscriptionDao dao, UserService userService) {
        this.dao = dao;
        this.userService = userService;
    }

    @Transactional
    public SubscriptionDTO create(UUID followerUuid, UUID followedUserUuid) {
        if(!followerUuid.equals(followedUserUuid)) {
            SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
            subscriptionDTO.setUuid(UUID.randomUUID());
            subscriptionDTO.setFollowerUuid(followerUuid);
            subscriptionDTO.setFollowedUserUuid(followedUserUuid);


            if (validate(subscriptionDTO)) {
                Subscription subscription = mapToEntity(subscriptionDTO);
                dao.save(subscription);
            }

            return subscriptionDTO;
        }
        else {
            throw new IllegalArgumentException("Unable to perform operation");
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

    //Нужно проверить нет ли в друзьях и если есть, удалить из друзей
    public boolean delete(UUID uuid) {

        Optional<Subscription> optional = dao.findById(uuid);

        if(optional.isPresent()) {
            UUID muuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

            if(muuid.equals(optional.get().getFollowerUuid())) {
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
