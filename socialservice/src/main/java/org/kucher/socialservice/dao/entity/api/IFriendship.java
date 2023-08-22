package org.kucher.socialservice.dao.entity.api;

import java.util.UUID;

public interface IFriendship {

    UUID getUuid();
    UUID getUser1Uuid();
    UUID getUser2Uuid();

}
