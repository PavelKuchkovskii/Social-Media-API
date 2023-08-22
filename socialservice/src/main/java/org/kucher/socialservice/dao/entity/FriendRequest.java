package org.kucher.socialservice.dao.entity;

import org.kucher.socialservice.dao.entity.api.IFriendRequest;
import org.kucher.socialservice.dao.entity.enums.EFriendRequestStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "friend_request")
public class FriendRequest implements IFriendRequest {

    @Id
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    @Column(name = "sender_uuid")
    private UUID senderUuid;
    @Column(name = "receiver_uuid")
    private UUID receiverUuid;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EFriendRequestStatus status;

    public FriendRequest() {
    }
    public FriendRequest(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, UUID senderUuid, UUID receiverUuid, EFriendRequestStatus status) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.senderUuid = senderUuid;
        this.receiverUuid = receiverUuid;
        this.status = status;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    @Override
    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    @Override
    public UUID getSenderUuid() {
        return senderUuid;
    }

    @Override
    public UUID getReceiverUuid() {
        return receiverUuid;
    }

    @Override
    public EFriendRequestStatus getStatus() {
        return status;
    }


}
