package org.kucher.socialservice.dao.entity;

import org.kucher.socialservice.dao.entity.api.IFriendRequest;
import org.kucher.socialservice.dao.entity.enums.EFriendRequestStatus;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "friend_request")
public class FriendRequest implements IFriendRequest {

    @Id
    private UUID uuid;
    private User sender;
    private User receiver;
    private EFriendRequestStatus status;

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public User getSenderUuid() {
        return this.sender;
    }

    @Override
    public User getReceiverUuid() {
        return this.receiver;
    }

    @Override
    public EFriendRequestStatus getStatus() {
        return this.status;
    }
}
