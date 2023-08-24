package org.kucher.userservice.service;

import org.kucher.userservice.exception.crud.UserAlreadyExistsException;
import org.kucher.userservice.exception.crud.UserAlreadyUpdatedException;
import org.kucher.userservice.service.api.IUserService;
import org.kucher.userservice.utill.Time.TimeUtil;
import org.kucher.userservice.repository.IUserDao;
import org.kucher.userservice.model.User;
import org.kucher.userservice.model.builder.UserBuilder;
import org.kucher.userservice.model.enums.EUserRole;
import org.kucher.userservice.model.enums.EUserStatus;
import org.kucher.userservice.dto.UserByAdminDTO;
import org.kucher.userservice.dto.UserCreateDTO;
import org.kucher.userservice.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


/**
 * Service implementation for managing user-related operations.
 */
@Service
@Transactional(readOnly = true)
public class UserService implements IUserService {

    private final IUserDao dao;
    private final ModelMapper mapper;
    private final PasswordEncoder encoder;

    public UserService(IUserDao dao, ModelMapper mapper, PasswordEncoder encoder) {
        this.dao = dao;
        this.mapper = mapper;
        this.encoder = encoder;
    }

    /**
     * Creates a new user based on the provided UserCreateDTO.
     *
     * @param dto The UserCreateDTO containing user information for creation.
     * @return The UserDTO representing the created user.
     * @throws UserAlreadyExistsException If a user with the provided email already exists.
     * @throws RuntimeException If an error occurs during user creation or retrieval.
     */
    @Override
    @Transactional
    public UserDTO create(UserCreateDTO dto) {

        if(dao.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exist");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(UUID.randomUUID());
        userDTO.setDtCreate(TimeUtil.now());
        userDTO.setDtUpdate(userDTO.getDtCreate());
        userDTO.setEmail(dto.getEmail());
        userDTO.setName(dto.getName());
        userDTO.setSurname(dto.getSurname());
        userDTO.setPassword(encoder.encode(dto.getPassword()));
        userDTO.setRole(EUserRole.ROLE_USER);
        userDTO.setStatus(EUserStatus.ACTIVATED);

        if(validate(userDTO)) {
            User user = mapToEntity(userDTO);
            dao.save(user);
        }
        try {
            return this.read(userDTO.getUuid());
        }
        catch (Exception e) {
            throw new RuntimeException("Something was wrong. Try again later");
        }
    }

    /**
     * Creates a new user by an admin based on the provided UserByAdminDTO.
     *
     * @param dto The UserByAdminDTO containing user information for creation by an admin.
     * @return The UserDTO representing the created user.
     * @throws UserAlreadyExistsException If a user with the provided email already exists.
     * @throws RuntimeException If an error occurs during user creation or retrieval.
     */
    @Override
    @Transactional
    public UserDTO create(UserByAdminDTO dto) {

        if(dao.findByEmail(dto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exist");
        }

        UserDTO userDTO = new UserDTO();
        userDTO.setUuid(UUID.randomUUID());
        userDTO.setDtCreate(TimeUtil.now());
        userDTO.setDtUpdate(userDTO.getDtCreate());
        userDTO.setEmail(dto.getEmail());
        userDTO.setName(dto.getName());
        userDTO.setSurname(dto.getSurname());
        userDTO.setPassword(encoder.encode(dto.getPassword()));
        userDTO.setRole(EUserRole.get(dto.getRole()));
        userDTO.setStatus(EUserStatus.get(dto.getStatus()));

        if(validate(userDTO)) {
            User user = mapToEntity(userDTO);
            dao.save(user);
        }

        return this.read(userDTO.getUuid());
    }

    /**
     * Updates user information with the provided UserByAdminDTO.
     *
     * @param uuid The UUID of the user to be updated.
     * @param dtUpdate The LocalDateTime representing the last update timestamp of the user.
     * @param dto The UserByAdminDTO containing updated user information.
     * @return The UserDTO representing the updated user.
     * @throws IllegalArgumentException If the provided UUID is invalid.
     * @throws UserAlreadyUpdatedException If the user has already been updated.
     * @throws RuntimeException If an error occurs during user update or retrieval.
     */
    @Override
    @Transactional
    public UserDTO update(UUID uuid, LocalDateTime dtUpdate, UserByAdminDTO dto) {

        UserDTO userDTO;

        try {
            userDTO = read(uuid);
        } catch (RuntimeException ex) {
            throw new IllegalArgumentException("Invalid uuid");
        }

        if(dtUpdate.isEqual(userDTO.getDtUpdate())) {
            userDTO.setDtUpdate(TimeUtil.now());
            userDTO.setEmail(dto.getEmail());
            userDTO.setName(dto.getName());
            userDTO.setSurname(dto.getSurname());
            userDTO.setPassword(encoder.encode(dto.getPassword()));
            userDTO.setRole(EUserRole.get(dto.getRole()));
            userDTO.setStatus(EUserStatus.get(dto.getStatus()));

            if (validate(userDTO)) {
                User user = mapToEntity(userDTO);
                dao.save(user);
            }

            try {
                return this.read(userDTO.getUuid());
            }
            catch (Exception e) {
                throw new RuntimeException("Something was wrong. Try again later");
            }
        }
        else {
            throw new UserAlreadyUpdatedException("User already updated");
        }
    }

    /**
     * Retrieves user information by UUID.
     *
     * @param uuid The UUID of the user to be retrieved.
     * @return The UserDTO representing the retrieved user.
     * @throws EntityNotFoundException If the user with the provided UUID is not found.
     */
    @Override
    public UserDTO read(UUID uuid) {
        Optional<User> user = dao.findById(uuid);

        if(user.isEmpty()) {
            throw new EntityNotFoundException("User not found exception");
        }

        return this.mapToDTO(user.get());
    }

    @Override
    public boolean validate(UserDTO dto) {
        if (dto.getUuid() == null) {
            throw new IllegalArgumentException("Uuid cannot be null");
        }
        else if(dto.getDtCreate() == null) {
            throw new IllegalArgumentException("Create date cannot be null");
        }
        else if(dto.getDtUpdate() == null) {
            throw new IllegalArgumentException("Update date cannot be null");
        }
        else if(dto.getRole() == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        else if(dto.getStatus() == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        return true;
    }

    @Override
    public UserDTO mapToDTO(User user) {
        return mapper.map(user, UserDTO.class);
    }

    @Override
    public User mapToEntity(UserDTO userDTO) {
        return UserBuilder
                .create()
                .setUuid(userDTO.getUuid())
                .setDtCreate(userDTO.getDtCreate())
                .setDtUpdate(userDTO.getDtUpdate())
                .setEmail(userDTO.getEmail())
                .setName(userDTO.getName())
                .setSurname(userDTO.getSurname())
                .setPassword(userDTO.getPassword())
                .setRole(userDTO.getRole())
                .setStatus(userDTO.getStatus())
                .build();
    }
}