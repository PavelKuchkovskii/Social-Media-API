package org.kucher.socialservice.dto.friendrequest;

import java.util.UUID;

public class CreateFriendRequestDTO {
    private UUID receiverUuid;

    public CreateFriendRequestDTO(UUID receiverUuid) {
        this.receiverUuid = receiverUuid;
    }

    public UUID getReceiverUuid() {
        return receiverUuid;
    }

    public void setReceiverUuid(UUID receiverUuid) {
        this.receiverUuid = receiverUuid;
    }


}
