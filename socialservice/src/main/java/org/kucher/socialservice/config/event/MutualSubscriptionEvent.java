package org.kucher.socialservice.config.event;

import org.kucher.socialservice.dao.entity.FriendRequest;
import org.springframework.context.ApplicationEvent;

public class FriendshipAcceptedEvent extends ApplicationEvent {
    public FriendshipAcceptedEvent(FriendRequest friendRequest) {
        super(friendRequest);
    }

    public FriendRequest getFriendRequest() {
        return (FriendRequest) getSource();
    }
}