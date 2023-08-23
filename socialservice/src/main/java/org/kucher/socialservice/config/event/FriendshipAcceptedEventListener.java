package org.kucher.socialservice.config.event;

import org.kucher.socialservice.service.SubscriptionService;
import org.kucher.socialservice.service.dto.subscription.CreateSubscriptionDTO;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class FriendshipAcceptedEventListener implements ApplicationListener<FriendshipAcceptedEvent> {

    private final SubscriptionService subscriptionService;

    public FriendshipAcceptedEventListener(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public void onApplicationEvent(FriendshipAcceptedEvent event) {
        //Create subscription
        subscriptionService.createFromFriendRequestService(event.getFriendRequest());
    }
}