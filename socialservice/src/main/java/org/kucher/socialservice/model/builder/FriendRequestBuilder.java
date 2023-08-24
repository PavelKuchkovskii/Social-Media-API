package org.kucher.socialservice.model.builder;

import org.kucher.socialservice.model.FriendRequest;
import org.kucher.socialservice.model.enums.EFriendRequestStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class FriendRequestBuilder {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private UUID senderUuid;
    private UUID receiverUuid;

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

    public FriendRequestBuilder setSender(UUID senderUuid) {
        this.senderUuid = senderUuid;
        return this;
    }

    public FriendRequestBuilder setReceiver(UUID receiverUuid) {
        this.receiverUuid = receiverUuid;
        return this;
    }

    public FriendRequestBuilder setStatus(EFriendRequestStatus status) {
        this.status = status;
        return this;
    }

    public FriendRequest build() {
        return new FriendRequest(uuid, dtCreate, dtUpdate, senderUuid, receiverUuid, status);
    }

}
