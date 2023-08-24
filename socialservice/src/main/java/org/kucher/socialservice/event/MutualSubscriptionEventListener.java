package org.kucher.socialservice.event;

import org.kucher.socialservice.model.FriendRequest;
import org.kucher.socialservice.service.FriendshipServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Listener class to handle mutual subscription events.
 */
@Component
public class MutualSubscriptionEventListener implements ApplicationListener<MutualSubscriptionEvent> {

    private final FriendshipServiceImpl friendshipServiceImpl;

    public MutualSubscriptionEventListener(FriendshipServiceImpl friendshipServiceImpl) {
        this.friendshipServiceImpl = friendshipServiceImpl;
    }


    /**
     * Handles the mutual subscription event by creating a friendship based on the subscription.
     *
     * @param event The mutual subscription event.
     */
    @Override
    public void onApplicationEvent(MutualSubscriptionEvent event) {
        //Create friendship
        friendshipServiceImpl.create(new FriendRequest(event.getSubscription().getFollowerUuid(), event.getSubscription().getFollowedUserUuid()));
    }
}