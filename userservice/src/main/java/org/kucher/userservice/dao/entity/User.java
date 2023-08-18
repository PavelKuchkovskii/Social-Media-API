package org.kucher.userservice.dao.entity;

import org.kucher.userservice.dao.entity.api.IUser;
import org.kucher.userservice.dao.entity.enums.EUserRole;
import org.kucher.userservice.dao.entity.enums.EUserStatus;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user")
public class User implements IUser {
    @Id
    private UUID uuid;
    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private EUserRole role;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private EUserStatus status;

    public User() {
    }

    public User(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, String mail, String name,String surname, String password, EUserRole role, EUserStatus status) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.email = mail;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.role = role;
        this.status = status;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public LocalDateTime getDtCreate() {
        return this.dtCreate;
    }

    @Override
    public LocalDateTime getDtUpdate() {
        return this.dtUpdate;
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

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public EUserRole getRole() {
        return this.role;
    }

    @Override
    public EUserStatus getStatus() {
        return this.status;
    }
}
