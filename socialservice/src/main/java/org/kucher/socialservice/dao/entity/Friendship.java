package org.kucher.socialservice.dao.entity;

import org.kucher.socialservice.dao.entity.api.IFriendship;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "friendship")
public class Friendship implements IFriendship {

    @Id
    private UUID uuid;

    @Column(name = "user1_uuid")
    private UUID user1Uuid;

    @Column(name = "user2_uuid")
    private UUID user2Uuid;

    public Friendship() {
    }

    public Friendship(UUID uuid, UUID user1Uuid, UUID user2Uuid) {
        this.uuid = uuid;
        this.user1Uuid = user1Uuid;
        this.user2Uuid = user2Uuid;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public UUID getUser1Uuid() {
        return this.user1Uuid;
    }

    @Override
    public UUID getUser2Uuid() {
        return this.user2Uuid;
    }
}
