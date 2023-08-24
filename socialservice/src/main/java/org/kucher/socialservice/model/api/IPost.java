package org.kucher.socialservice.model.api;

import java.util.UUID;

/**
 * Interface representing a post entity.
 */
public interface IPost extends IEssence {

    /**
     * Gets the UUID of the user who created the post.
     *
     * @return The UUID of the user.
     */
    UUID getUserUuid();

    /**
     * Gets the text content of the post.
     *
     * @return The text content of the post.
     */
    String getText();

    /**
     * Gets the title of the post.
     *
     * @return The title of the post.
     */
    String getTitle();

    /**
     * Gets the image data of the post.
     *
     * @return The image data of the post as a byte array.
     */
    byte[] getImage();
}
