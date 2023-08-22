package org.kucher.socialservice.service.api;

import org.kucher.socialservice.service.dto.CreatePostDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IService<CREATE_DTO, DTO, ENTITY> {

    DTO create(CREATE_DTO dto);
    DTO read(UUID uuid);
    DTO update(CREATE_DTO dto,UUID uuid, LocalDateTime dtUpdate);
    boolean delete(UUID uuid);
    boolean validate(CREATE_DTO dto);

    ENTITY mapToEntity(CreatePostDTO dto);
    DTO mapToDTO(ENTITY entity);
}
