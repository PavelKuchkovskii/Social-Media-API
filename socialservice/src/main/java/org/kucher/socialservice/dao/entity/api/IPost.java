package org.kucher.socialservice.dao.entity.api;

import java.util.UUID;

public interface IPost extends IEssence {

    UUID getUserUuid();
    String getText();
    String getTitle();
    byte[] getImage();

}
