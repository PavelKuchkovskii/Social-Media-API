package org.kucher.socialservice.dao.entity.builder;

import org.kucher.socialservice.dao.entity.FriendRequest;
import org.kucher.socialservice.dao.entity.User;
import org.kucher.socialservice.dao.entity.enums.EFriendRequestStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class FriendRequestBuilder {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private User sender;
    private User receiver;

    private EFriendRequestStatus status;

    private FriendRequestBuilder() {
    }

    public static FriendRequestBuilder create() {
        return new FriendRequestBuilder();
    }

    public FriendRequestBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public FriendRequestBuilder setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public FriendRequestBuilder setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public FriendRequestBuilder setSender(User sender) {
        this.sender = sender;
        return this;
    }

    public FriendRequestBuilder setReceiver(User receiver) {
        this.receiver = receiver;
        return this;
    }

    public FriendRequestBuilder setStatus(EFriendRequestStatus status) {
        this.status = status;
        return this;
    }

    public FriendRequest build() {
        return new FriendRequest(uuid, dtCreate, dtUpdate, sender, receiver, status);
    }

}
