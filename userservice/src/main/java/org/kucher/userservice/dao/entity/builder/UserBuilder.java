package org.kucher.userservice.dao.entity.builder;


import org.kucher.userservice.dao.entity.User;
import org.kucher.userservice.dao.entity.enums.EUserRole;
import org.kucher.userservice.dao.entity.enums.EUserStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserBuilder {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private String email;
    private String name;
    private String surname;
    private String password;
    private EUserRole role;
    private EUserStatus status;

    private UserBuilder () {

    }

    public static UserBuilder create() {
        return new UserBuilder();
    }

    public UserBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UserBuilder setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public UserBuilder setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public UserBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder setRole(EUserRole role) {
        this.role = role;
        return this;
    }

    public UserBuilder setStatus(EUserStatus status) {
        this.status = status;
        return this;
    }

    public User build () {
        return new User(uuid, dtCreate, dtUpdate, email, name, surname, password, role, status);
    }
}
