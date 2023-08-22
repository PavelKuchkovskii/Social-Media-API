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
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sender_uuid")
    private User sender;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "receiver_uuid")
    private User receiver;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EFriendRequestStatus status;

    public FriendRequest() {
    }

    public FriendRequest(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, User sender, User receiver, EFriendRequestStatus status) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.sender = sender;
        this.receiver = receiver;
        this.status = status;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public LocalDateTime getDtCreate() {
        return this.dtCreate;
    }

    @Override
    public LocalDateTime getDtUpdate() {
        return this.dtUpdate;
    }

    @Override
    public User getSender() {
        return this.sender;
    }

    @Override
    public User getReceiver() {
        return this.receiver;
    }

    @Override
    public EFriendRequestStatus getStatus() {
        return this.status;
    }
}
