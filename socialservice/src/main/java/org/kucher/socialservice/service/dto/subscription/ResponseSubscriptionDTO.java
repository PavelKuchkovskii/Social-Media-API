package org.kucher.socialservice.service.dto.subscription;

import org.kucher.socialservice.dao.entity.User;

import java.util.UUID;

public class ResponseSubscriptionDTO {
    private UUID uuid;
    private User followerUuid;
    private User followedUserUuid;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public User getFollowerUuid() {
        return followerUuid;
    }

    public void setFollowerUuid(User followerUuid) {
        this.followerUuid = followerUuid;
    }

    public User getFollowedUserUuid() {
        return followedUserUuid;
    }

    public void setFollowedUserUuid(User followedUserUuid) {
        this.followedUserUuid = followedUserUuid;
    }
}
