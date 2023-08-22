package org.kucher.socialservice.service.api;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IService<CREATE_DTO, UPDATE_DTO, RESPONSE_DTO, DTO, ENTITY> {

    RESPONSE_DTO create(CREATE_DTO dto);
    RESPONSE_DTO read(UUID uuid);
    RESPONSE_DTO update(UPDATE_DTO dto,UUID uuid, LocalDateTime dtUpdate);
    boolean delete(UUID uuid);
    boolean validate(DTO dto);

    ENTITY mapToEntity(DTO dto);
    RESPONSE_DTO mapToDTO(ENTITY entity);
}
