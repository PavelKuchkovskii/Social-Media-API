package org.kucher.userservice.controller;

import org.kucher.userservice.service.UserService;
import org.kucher.userservice.service.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;
    //private final ConfirmRegistrationService confirmService;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> doGet() {

        UUID uuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());
        UserDTO user = this.service.read(uuid);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /*@GetMapping("/registration/confirm/{token}")
    public ResponseEntity<RegistrationMessage> doConfirm(@PathVariable("token") String token) {

        confirmService.confirmRegistration(token);

        RegistrationMessage message = new RegistrationMessage("info", "User activated, login using credentials");

        return new ResponseEntity<>(message, HttpStatus.OK);
    }*/



}