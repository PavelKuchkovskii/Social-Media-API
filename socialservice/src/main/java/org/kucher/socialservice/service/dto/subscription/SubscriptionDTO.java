package org.kucher.socialservice.service.dto.subscription;

import java.util.UUID;

public class SubscriptionDTO {
    private UUID uuid;
    private UUID followerUuid;
    private UUID followedUserUuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getFollowerUuid() {
        return followerUuid;
    }

    public void setFollowerUuid(UUID followerUuid) {
        this.followerUuid = followerUuid;
    }

    public UUID getFollowedUserUuid() {
        return followedUserUuid;
    }

    public void setFollowedUserUuid(UUID followedUserUuid) {
        this.followedUserUuid = followedUserUuid;
    }
}
