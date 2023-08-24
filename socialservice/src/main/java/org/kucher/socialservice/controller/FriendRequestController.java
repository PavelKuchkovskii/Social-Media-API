package org.kucher.socialservice.controller;

import org.kucher.socialservice.service.FriendRequestServiceImpl;
import org.kucher.socialservice.dto.friendrequest.ResponseFriendRequestDTO;
import org.kucher.socialservice.dto.friendrequest.UpdateFriendRequestDTO;
import org.kucher.socialservice.service.api.IFriendRequestService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/friend")
public class FriendRequestController {

    private final IFriendRequestService service;

    public FriendRequestController(IFriendRequestService service) {
        this.service = service;
    }

    @PatchMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<ResponseFriendRequestDTO> doUpdate(@Valid @RequestBody UpdateFriendRequestDTO dto, @PathVariable("uuid") UUID uuid, @PathVariable("dt_update") LocalDateTime dtUpdate) {

        ResponseFriendRequestDTO created = service.update(dto, uuid, dtUpdate);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @GetMapping("/request")
    public ResponseEntity<Page<ResponseFriendRequestDTO>> doGet(@RequestParam int page, @RequestParam int size) {

        UUID uuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        Page<ResponseFriendRequestDTO> responseFriendRequestDTOS = this.service.read(uuid, page, size);

        return new ResponseEntity<>(responseFriendRequestDTOS, HttpStatus.OK);
    }
}
