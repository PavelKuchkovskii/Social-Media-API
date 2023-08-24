package org.kucher.socialservice.model.api;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * This interface defines the common properties that entities should have.
 */
public interface IEssence {

    /**
     * Returns the UUID (Universally Unique Identifier) associated with this entity.
     *
     * @return The UUID of the entity.
     */
    UUID getUuid();

    /**
     * Returns the date and time when this entity was created.
     *
     * @return The creation date and time of the entity.
     */
    LocalDateTime getDtCreate();

    /**
     * Returns the date and time when this entity was last updated.
     *
     * @return The last update date and time of the entity.
     */
    LocalDateTime getDtUpdate();
}
