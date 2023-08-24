package org.kucher.socialservice.model.api;

import java.util.UUID;

public interface ISubscription {

    UUID getUuid();
    UUID getFollowerUuid();
    UUID getFollowedUserUuid();

}
