package org.kucher.socialservice.config.event;

import org.kucher.socialservice.dao.entity.FriendRequest;
import org.kucher.socialservice.service.FriendshipService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MutualSubscriptionEventListener implements ApplicationListener<MutualSubscriptionEvent> {

    private final FriendshipService friendshipService;

    public MutualSubscriptionEventListener(FriendshipService friendshipService) {
        this.friendshipService = friendshipService;
    }

    @Override
    public void onApplicationEvent(MutualSubscriptionEvent event) {
        //Create friendship
        friendshipService.create(new FriendRequest(event.getSubscription().getFollowerUuid(), event.getSubscription().getFollowedUserUuid()));
    }
}