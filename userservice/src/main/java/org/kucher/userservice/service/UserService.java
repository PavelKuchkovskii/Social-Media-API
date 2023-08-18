package org.kucher.userservice.service;

import org.kucher.userservice.config.exception.api.crud.UserAlreadyExistsException;
import org.kucher.userservice.config.exception.api.crud.UserAlreadyUpdatedException;
import org.kucher.userservice.config.utill.Time.TimeUtil;
import org.kucher.userservice.dao.api.IUserDao;
import org.kucher.userservice.dao.entity.User;
import org.kucher.userservice.dao.entity.builder.UserBuilder;
import org.kucher.userservice.dao.entity.enums.EUserRole;
import org.kucher.userservice.dao.entity.enums.EUserStatus;
import org.kucher.userservice.service.dto.UserByAdminDTO;
import org.kucher.userservice.service.dto.UserCreateDTO;
import org.kucher.userservice.service.dto.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserService {

    private final IUserDao dao;
    private final ModelMapper mapper;
    private final PasswordEncoder encoder;

    public UserService(IUserDao dao, ModelMapper mapper, PasswordEncoder encoder) {
        this.dao = dao;
        this.mapper = mapper;
        this.encoder = encoder;
    }
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

        return this.read(userDTO.getUuid());
    }

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

            return this.read(userDTO.getUuid());
        }
        else {
            throw new UserAlreadyUpdatedException("User already updated");
        }
    }

    public UserDTO read(UUID uuid) {
        Optional<User> user = dao.findById(uuid);

        if(user.isEmpty()) {
            //другая ошибка должна быть
            throw new RuntimeException("Something wrong");
        }

        return this.mapToDTO(user.get());
    }


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

    public UserDTO mapToDTO(User user) {
        return mapper.map(user, UserDTO.class);
    }

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