package org.kucher.socialservice.service.dto.friendrequest;

import org.kucher.socialservice.dao.entity.enums.EFriendRequestStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class FriendRequestDTO {
    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private UUID senderUuid;
    private UUID receiverUuid;
    private EFriendRequestStatus status;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public UUID getSenderUuid() {
        return senderUuid;
    }

    public void setSenderUuid(UUID senderUuid) {
        this.senderUuid = senderUuid;
    }

    public UUID getReceiverUuid() {
        return receiverUuid;
    }

    public void setReceiverUuid(UUID receiverUuid) {
        this.receiverUuid = receiverUuid;
    }

    public EFriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(EFriendRequestStatus status) {
        this.status = status;
    }
}
