package org.kucher.socialservice.model.builder;

import org.kucher.socialservice.model.Subscription;

import java.util.UUID;

public class SubscriptionBuilder {

    private UUID uuid;

    private UUID followerUuid;
    private UUID followedUserUuid;

    private SubscriptionBuilder(){

    }
    public static SubscriptionBuilder create() {
        return new SubscriptionBuilder();
    }

    public SubscriptionBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public SubscriptionBuilder setFollower(UUID followerUuid) {
        this.followerUuid = followerUuid;
        return this;
    }

    public SubscriptionBuilder setFollowedUser(UUID followedUserUuid) {
        this.followedUserUuid = followedUserUuid;
        return this;
    }

    public Subscription build(){
        return new Subscription(uuid, followerUuid, followedUserUuid);
    }
}
