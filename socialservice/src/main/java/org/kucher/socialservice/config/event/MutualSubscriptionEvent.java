package org.kucher.socialservice.config.event;

import org.kucher.socialservice.service.dto.subscription.SubscriptionDTO;
import org.springframework.context.ApplicationEvent;

public class MutualSubscriptionEvent extends ApplicationEvent {
    public MutualSubscriptionEvent(SubscriptionDTO subscriptionDTO) {
        super(subscriptionDTO);
    }

    public SubscriptionDTO getSubscription() {
        return (SubscriptionDTO) getSource();
    }
}