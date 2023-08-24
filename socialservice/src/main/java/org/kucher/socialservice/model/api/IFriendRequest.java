package org.kucher.socialservice.model.api;

import org.kucher.socialservice.model.enums.EFriendRequestStatus;

import java.util.UUID;

public interface IFriendRequest extends IEssence {

    UUID getUuid();
    UUID getSenderUuid();
    UUID getReceiverUuid();

    EFriendRequestStatus getStatus();
}
