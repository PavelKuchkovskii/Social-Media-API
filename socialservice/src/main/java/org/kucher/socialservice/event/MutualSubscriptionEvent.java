package org.kucher.socialservice.event;

import org.kucher.socialservice.dto.subscription.SubscriptionDTO;
import org.springframework.context.ApplicationEvent;

/**
 * Custom event class representing a mutual subscription event.
 */
public class MutualSubscriptionEvent extends ApplicationEvent {
    public MutualSubscriptionEvent(SubscriptionDTO subscriptionDTO) {
        super(subscriptionDTO);
    }

    public SubscriptionDTO getSubscription() {
        return (SubscriptionDTO) getSource();
    }
}