package org.kucher.socialservice.dao.entity.api;

import org.kucher.socialservice.dao.entity.User;
import org.kucher.socialservice.dao.entity.enums.EFriendRequestStatus;

import java.util.UUID;

public interface IFriendRequest extends IEssence {

    UUID getUuid();
    User getSender();
    User getReceiver();

    EFriendRequestStatus getStatus();
}
