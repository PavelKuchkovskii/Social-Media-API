package org.kucher.socialservice.dao.entity;


import org.kucher.socialservice.dao.entity.api.IUser;

import java.util.UUID;

public class User implements IUser {
    private UUID uuid;
    private String email;
    private String name;
    private String surname;

    public User() {
    }

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public User(UUID uuid, String email, String name, String surname) {
        this.uuid = uuid;
        this.email = email;
        this.name = name;
        this.surname = surname;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }
    public String getEmail() {
        return this.email;
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public String getSurname() {
        return this.surname;
    }


}
