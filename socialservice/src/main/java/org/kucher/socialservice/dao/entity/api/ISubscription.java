package org.kucher.socialservice.dao.entity.api;

import org.kucher.socialservice.dao.entity.User;

import java.util.UUID;

public interface ISubscription {

    UUID getUuid();
    UUID getFollowerUuid();
    UUID getFollowedUserUuid();

}
