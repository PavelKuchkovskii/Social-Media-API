package org.kucher.socialservice.model.api;

import java.util.UUID;

public interface IPost extends IEssence {

    UUID getUserUuid();
    String getText();
    String getTitle();
    byte[] getImage();

}
