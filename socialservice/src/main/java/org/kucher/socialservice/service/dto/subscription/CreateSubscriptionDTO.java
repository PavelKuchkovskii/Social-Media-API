package org.kucher.socialservice.service.dto.subscription;

import java.util.UUID;

public class CreateSubscriptionDTO {
    private UUID followedUserUuid;

    public CreateSubscriptionDTO() {
    }

    public CreateSubscriptionDTO(UUID followedUserUuid) {
        this.followedUserUuid = followedUserUuid;
    }

    public UUID getFollowedUserUuid() {
        return followedUserUuid;
    }

    public void setFollowedUserUuid(UUID followedUserUuid) {
        this.followedUserUuid = followedUserUuid;
    }
}
