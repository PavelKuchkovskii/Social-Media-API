package org.kucher.socialservice.model.api;

import java.util.UUID;

public interface IFriendship {

    UUID getUuid();
    UUID getUser1Uuid();
    UUID getUser2Uuid();

}
