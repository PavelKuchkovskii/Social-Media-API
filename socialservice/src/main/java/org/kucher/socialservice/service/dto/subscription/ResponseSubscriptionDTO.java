package org.kucher.socialservice.service.dto.subscription;

import org.kucher.socialservice.dao.entity.User;

import java.util.UUID;

public class ResponseSubscriptionDTO {
    private UUID uuid;
    private User follower;
    private User followedUser;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowedUser() {
        return followedUser;
    }

    public void setFollowedUser(User followedUser) {
        this.followedUser = followedUser;
    }
}
