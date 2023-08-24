package org.kucher.socialservice.event;

import org.kucher.socialservice.model.FriendRequest;
import org.springframework.context.ApplicationEvent;

public class FriendshipAcceptedEvent extends ApplicationEvent {
    public FriendshipAcceptedEvent(FriendRequest friendRequest) {
        super(friendRequest);
    }

    public FriendRequest getFriendRequest() {
        return (FriendRequest) getSource();
    }
}