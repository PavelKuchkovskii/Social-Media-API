package org.kucher.userservice.model;

import org.kucher.userservice.model.api.IUser;
import org.kucher.userservice.model.enums.EUserRole;
import org.kucher.userservice.model.enums.EUserStatus;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "users")
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

    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", dtCreate=" + dtCreate +
                ", dtUpdate=" + dtUpdate +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uuid, user.uuid) && Objects.equals(dtCreate, user.dtCreate) && Objects.equals(dtUpdate, user.dtUpdate) && Objects.equals(email, user.email) && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(password, user.password) && role == user.role && status == user.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, dtCreate, dtUpdate, email, name, surname, password, role, status);
    }
}
