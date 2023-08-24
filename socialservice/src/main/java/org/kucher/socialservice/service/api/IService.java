package org.kucher.socialservice.service.api;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Interface for a service handling CRUD operations on entities.
 *
 * @param <CREATE_DTO> Type of Data Transfer Object for creating an entity.
 * @param <UPDATE_DTO> Type of Data Transfer Object for updating an entity.
 * @param <RESPONSE_DTO> Type of Data Transfer Object for response.
 * @param <DTO> Common Data Transfer Object type.
 * @param <ENTITY> Entity type.
 */
public interface IService<CREATE_DTO, UPDATE_DTO, RESPONSE_DTO, DTO, ENTITY> {

    /**
     * Creates a new entity based on the provided Data Transfer Object.
     *
     * @param dto Data Transfer Object containing data for creating the entity.
     * @return Data Transfer Object with data of the created entity.
     */
    RESPONSE_DTO create(CREATE_DTO dto);

    /**
     * Reads an entity based on the provided identifier.
     *
     * @param uuid Identifier of the entity.
     * @return Data Transfer Object with data of the entity.
     */
    RESPONSE_DTO read(UUID uuid);

    /**
     * Updates an entity based on the provided Data Transfer Object.
     *
     * @param dto Data Transfer Object containing data for updating the entity.
     * @param uuid Identifier of the entity.
     * @param dtUpdate The date and time the entity was last updated.
     * @return Data Transfer Object with updated entity data.
     */
    RESPONSE_DTO update(UPDATE_DTO dto,UUID uuid, LocalDateTime dtUpdate);

    /**
     * Deletes an entity based on the provided identifier.
     *
     * @param uuid Identifier of the entity to be deleted.
     * @return true if the deletion was successful, otherwise false.
     */
    boolean delete(UUID uuid);

    /**
     * Validates the Data Transfer Object.
     *
     * @param dto Data Transfer Object to validate.
     * @return true if the Data Transfer Object is valid, otherwise false.
     */
    boolean validate(DTO dto);

    /**
     * Converts a Data Transfer Object to an entity.
     *
     * @param dto Data Transfer Object to be converted.
     * @return Entity based on the Data Transfer Object.
     */
    ENTITY mapToEntity(DTO dto);

    /**
     * Converts an entity to a Data Transfer Object for response.
     *
     * @param entity Entity to be converted.
     * @return Data Transfer Object with entity data.
     */
    RESPONSE_DTO mapToDTO(ENTITY entity);
}
