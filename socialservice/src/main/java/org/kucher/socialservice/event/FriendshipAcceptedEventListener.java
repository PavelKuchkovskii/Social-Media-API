package org.kucher.socialservice.event;

import org.kucher.socialservice.service.SubscriptionServiceImpl;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Event listener class that handles the FriendshipAcceptedEvent.
 */
@Component
public class FriendshipAcceptedEventListener implements ApplicationListener<FriendshipAcceptedEvent> {

    private final SubscriptionServiceImpl subscriptionServiceImpl;

    public FriendshipAcceptedEventListener(SubscriptionServiceImpl subscriptionServiceImpl) {
        this.subscriptionServiceImpl = subscriptionServiceImpl;
    }

    /**
     * Handles the FriendshipAcceptedEvent by creating a subscription.
     *
     * @param event The FriendshipAcceptedEvent to be handled.
     */
    @Override
    public void onApplicationEvent(FriendshipAcceptedEvent event) {
        //Create subscription
        subscriptionServiceImpl.createFromFriendRequestService(event.getFriendRequest());
    }
}