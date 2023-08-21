package org.kucher.socialservice.service.api;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IService<DTO, ENTITY> {

    DTO create(DTO dto);
    DTO read(UUID uuid);
    DTO update(DTO dto,UUID uuid, LocalDateTime dtUpdate);
    boolean delete(UUID uuid);
    boolean validate(DTO dto);

    ENTITY mapToEntity(DTO dto);
    DTO mapToDTO(ENTITY entity);
}
