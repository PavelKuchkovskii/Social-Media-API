package org.kucher.userservice.controller;

import org.kucher.userservice.controller.api.IUserController;
import org.kucher.userservice.service.UserService;
import org.kucher.userservice.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserControllerImpl implements IUserController {

    private final UserService service;
    //private final ConfirmRegistrationService confirmService;

    public UserControllerImpl(UserService service) {
        this.service = service;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getMe() {

        UUID uuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        UserDTO user = this.service.read(uuid);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<UserDTO> getUserByUuid(@PathVariable("uuid")UUID uuid) {

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