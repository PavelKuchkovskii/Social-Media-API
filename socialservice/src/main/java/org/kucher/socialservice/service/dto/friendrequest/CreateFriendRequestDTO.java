package org.kucher.socialservice.service.dto.friendrequest;

import java.util.UUID;

public class CreateFriendRequestDTO {
    private UUID senderUuid;
    private UUID receiverUuid;

    public UUID getSenderUuid() {
        return senderUuid;
    }

    public void setSenderUuid(UUID senderUuid) {
        this.senderUuid = senderUuid;
    }

    public UUID getReceiverUuid() {
        return receiverUuid;
    }

    public void setReceiverUuid(UUID receiverUuid) {
        this.receiverUuid = receiverUuid;
    }
}
