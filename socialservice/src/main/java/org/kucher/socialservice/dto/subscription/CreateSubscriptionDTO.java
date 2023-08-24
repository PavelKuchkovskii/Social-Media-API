package org.kucher.socialservice.dto.subscription;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreateSubscriptionDTO {
    @NotNull(message = "Followed user cannot be blank")
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
