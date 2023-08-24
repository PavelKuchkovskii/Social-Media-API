package org.kucher.userservice.controller;

import org.kucher.userservice.controller.api.IAdminController;
import org.kucher.userservice.service.UserService;
import org.kucher.userservice.dto.UserByAdminDTO;
import org.kucher.userservice.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/users/admin/panel")
public class AdminControllerImpl implements IAdminController {

    private final UserService service;

    public AdminControllerImpl(UserService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<UserDTO> createUserByAdmin(@Valid @RequestBody UserByAdminDTO dto) {

        UserDTO created = this.service.create(dto);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @Override
    @PatchMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<UserDTO> updateUserByAdmin(@PathVariable("uuid") UUID uuid,
                                            @PathVariable("dt_update") LocalDateTime dtUpdate,
                                            @Valid @RequestBody UserByAdminDTO dto) {


        UserDTO created = this.service.update(uuid, dtUpdate, dto);

        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}
