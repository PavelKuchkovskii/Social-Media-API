package org.kucher.socialservice.service;

import org.kucher.socialservice.dao.api.ISubscriptionDao;
import org.kucher.socialservice.dao.entity.Subscription;
import org.kucher.socialservice.dao.entity.builder.SubscriptionBuilder;
import org.kucher.socialservice.service.dto.subscription.SubscriptionDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SubscriptionService {

    private final ISubscriptionDao dao;
    private final UserService userService;

    public SubscriptionService(ISubscriptionDao dao, UserService userService) {
        this.dao = dao;
        this.userService = userService;
    }

    @Transactional
    public SubscriptionDTO create(UUID followedUserUuid) {

        SubscriptionDTO subscriptionDTO = new SubscriptionDTO();
        subscriptionDTO.setUuid(UUID.randomUUID());
        subscriptionDTO.setFollowerUuid(followedUserUuid);
        subscriptionDTO.setFollowedUserUuid(followedUserUuid);


        if (validate(subscriptionDTO)) {
            Subscription subscription = mapToEntity(subscriptionDTO);
            dao.save(subscription);
        }

        return subscriptionDTO;
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
}
