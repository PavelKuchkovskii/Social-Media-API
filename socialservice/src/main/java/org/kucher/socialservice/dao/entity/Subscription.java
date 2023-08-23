package org.kucher.socialservice.dao.entity;

import org.kucher.socialservice.dao.entity.api.ISubscription;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "subscription", uniqueConstraints = @UniqueConstraint(columnNames = {"follower_uuid", "followedUser_uuid"}))
public class Subscription implements ISubscription {

    @Id
    private UUID uuid;

    @Column(name = "follower_uuid")
    private UUID followerUuid;

    @Column(name = "followedUser_uuid")
    private UUID followedUserUuid;

    public Subscription() {
    }

    public Subscription(UUID uuid, UUID followerUuid, UUID followedUserUuid) {
        this.uuid = uuid;
        this.followerUuid = followerUuid;
        this.followedUserUuid = followedUserUuid;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public UUID getFollowerUuid() {
        return followerUuid;
    }

    @Override
    public UUID getFollowedUserUuid() {
        return followedUserUuid;
    }
}
