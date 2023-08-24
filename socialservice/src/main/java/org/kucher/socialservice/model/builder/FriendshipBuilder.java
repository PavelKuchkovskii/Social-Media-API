package org.kucher.socialservice.model.builder;

import org.kucher.socialservice.model.Friendship;

import java.util.UUID;

public class FriendshipBuilder {

    private UUID uuid;

    private UUID user1Uuid;
    private UUID user2Uuid;

    private FriendshipBuilder(){

    }
    public static FriendshipBuilder create() {
        return new FriendshipBuilder();
    }

    public FriendshipBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public FriendshipBuilder setUser1Uuid(UUID user1Uuid) {
        this.user1Uuid = user1Uuid;
        return this;
    }

    public FriendshipBuilder setUser2Uuid(UUID user2Uuid) {
        this.user2Uuid = user2Uuid;
        return this;
    }

    public Friendship build(){
        return new Friendship(uuid, user1Uuid, user2Uuid);
    }
}
