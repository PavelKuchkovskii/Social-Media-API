package org.kucher.socialservice.controller;

import org.kucher.socialservice.service.FriendRequestService;
import org.kucher.socialservice.service.dto.friendrequest.CreateFriendRequestDTO;
import org.kucher.socialservice.service.dto.friendrequest.ResponseFriendRequestDTO;
import org.kucher.socialservice.service.dto.friendrequest.UpdateFriendRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/friend")
public class FriendRequestController {

    private final FriendRequestService service;

    public FriendRequestController(FriendRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseFriendRequestDTO> sendFriendRequest(@RequestBody CreateFriendRequestDTO dto) {

        ResponseFriendRequestDTO created = service.create(dto);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @PatchMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<ResponseFriendRequestDTO> doUpdate(@RequestBody UpdateFriendRequestDTO dto, @PathVariable("uuid") UUID uuid, @PathVariable("dt_update") LocalDateTime dtUpdate) {

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
