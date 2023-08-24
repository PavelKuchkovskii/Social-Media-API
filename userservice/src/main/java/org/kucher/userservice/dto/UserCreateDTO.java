package org.kucher.userservice.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UserCreateDTO {

    @NotBlank(message = "Mail cannot be blank")
    @Email(message = "Mail must be valid")
    private String email;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Surname cannot be blank")
    private String surname;
    private String password;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}