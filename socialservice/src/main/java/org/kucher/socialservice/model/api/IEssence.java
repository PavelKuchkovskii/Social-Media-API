package org.kucher.socialservice.model.api;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IEssence {
    UUID getUuid();

    LocalDateTime getDtCreate();

    LocalDateTime getDtUpdate();
}
