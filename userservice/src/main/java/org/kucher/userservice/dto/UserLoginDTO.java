package org.kucher.userservice.dto;

import javax.validation.constraints.NotBlank;

public class UserLoginDTO {

    @NotBlank(message = "Mail cannot be blank")
    private String email;
    @NotBlank(message = "Password cannot be blank")
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

    public void setPassword(String password) {
        this.password = password;
    }
}