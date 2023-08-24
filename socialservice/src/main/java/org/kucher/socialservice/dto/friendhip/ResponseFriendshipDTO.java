package org.kucher.socialservice.dto.friendhip;

import org.kucher.socialservice.model.User;

import java.util.UUID;

public class ResponseFriendshipDTO {
    private UUID uuid;
    private User user1;
    private User user2;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }
}
