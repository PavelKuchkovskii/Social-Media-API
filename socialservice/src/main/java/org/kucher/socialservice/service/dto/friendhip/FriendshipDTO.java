package org.kucher.socialservice.service.dto.friendhip;

import java.util.UUID;

public class FriendshipDTO {
    private UUID uuid;
    private UUID user1Uuid;
    private UUID user2Uuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUser1Uuid() {
        return user1Uuid;
    }

    public void setUser1Uuid(UUID user1Uuid) {
        this.user1Uuid = user1Uuid;
    }

    public UUID getUser2Uuid() {
        return user2Uuid;
    }

    public void setUser2Uuid(UUID user2Uuid) {
        this.user2Uuid = user2Uuid;
    }
}
