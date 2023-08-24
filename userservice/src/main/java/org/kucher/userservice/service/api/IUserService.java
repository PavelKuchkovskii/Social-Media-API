package org.kucher.userservice.service.api;

import org.kucher.userservice.dto.UserByAdminDTO;
import org.kucher.userservice.dto.UserCreateDTO;
import org.kucher.userservice.dto.UserDTO;
import org.kucher.userservice.model.User;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Service interface for managing user-related operations.
 */
public interface IUserService {

    /**
     * Creates a new user based on the provided UserCreateDTO.
     *
     * @param dto The data transfer object containing user information.
     * @return The data transfer object of the created user.
     */
    UserDTO create(UserCreateDTO dto);

    /**
     * Creates a new user by an admin based on the provided UserByAdminDTO.
     *
     * @param dto The data transfer object containing user information.
     * @return The data transfer object of the created user.
     */
    UserDTO create(UserByAdminDTO dto);

    /**
     * Updates the user information associated with the given UUID and timestamp.
     *
     * @param uuid The UUID of the user.
     * @param dtUpdate The timestamp of the update.
     * @param dto The data transfer object containing updated user information.
     * @return The data transfer object of the updated user.
     */
    UserDTO update(UUID uuid, LocalDateTime dtUpdate, UserByAdminDTO dto);

    /**
     * Retrieves the user information associated with the given UUID.
     *
     * @param uuid The UUID of the user.
     * @return The data transfer object of the retrieved user.
     */
    UserDTO read(UUID uuid);

    /**
     * Validates the user data represented by the provided UserDTO.
     *
     * @param dto The data transfer object containing user information.
     * @return True if the user data is valid, false otherwise.
     */
    boolean validate(UserDTO dto);

    /**
     * Maps a User entity to a UserDTO data transfer object.
     *
     * @param user The User entity to be mapped.
     * @return The data transfer object representing the mapped user.
     */
    UserDTO mapToDTO(User user);

    /**
     * Maps a UserDTO data transfer object to a User entity.
     *
     * @param userDTO The UserDTO data transfer object to be mapped.
     * @return The User entity representing the mapped user.
     */
    User mapToEntity(UserDTO userDTO);
}
