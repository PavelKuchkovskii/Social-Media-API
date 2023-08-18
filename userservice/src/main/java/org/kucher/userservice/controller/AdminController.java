package org.kucher.userservice.controller;

import org.kucher.userservice.service.UserService;
import org.kucher.userservice.service.dto.UserByAdminDTO;
import org.kucher.userservice.service.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class AdminController {

    private final UserService service;

    public AdminController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UserDTO> doPost(@Valid @RequestBody UserByAdminDTO dto) {

        UserDTO created = this.service.create(dto);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PatchMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<UserDTO> doUpdate(@PathVariable("uuid") UUID uuid,
                                            @PathVariable("dt_update") String dt_update,
                                            @Valid @RequestBody UserByAdminDTO dto) {

        LocalDateTime dtUpdate = LocalDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(dt_update)), ZoneOffset.UTC);

        UserDTO created = this.service.update(uuid, dtUpdate, dto);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
