package org.kucher.socialservice.service.dto.friendrequest;

import java.util.UUID;

public class CreateFriendRequestDTO {
    private UUID receiverUuid;

    public UUID getReceiverUuid() {
        return receiverUuid;
    }

    public void setReceiverUuid(UUID receiverUuid) {
        this.receiverUuid = receiverUuid;
    }
}
