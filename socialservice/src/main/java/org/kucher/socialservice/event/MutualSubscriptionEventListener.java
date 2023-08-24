package org.kucher.socialservice.event;

import org.kucher.socialservice.model.FriendRequest;
import org.kucher.socialservice.service.FriendshipServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class MutualSubscriptionEventListener implements ApplicationListener<MutualSubscriptionEvent> {

    private final FriendshipServiceImpl friendshipServiceImpl;

    public MutualSubscriptionEventListener(FriendshipServiceImpl friendshipServiceImpl) {
        this.friendshipServiceImpl = friendshipServiceImpl;
    }

    @Override
    public void onApplicationEvent(MutualSubscriptionEvent event) {
        //Create friendship
        friendshipServiceImpl.create(new FriendRequest(event.getSubscription().getFollowerUuid(), event.getSubscription().getFollowedUserUuid()));
    }
}