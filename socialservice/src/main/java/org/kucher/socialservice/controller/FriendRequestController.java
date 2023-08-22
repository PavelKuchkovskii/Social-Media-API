package org.kucher.socialservice.controller;

import org.kucher.socialservice.service.api.IFriendRequestService;
import org.kucher.socialservice.service.dto.friendrequest.CreateFriendRequestDTO;
import org.kucher.socialservice.service.dto.friendrequest.ResponseFriendRequestDTO;
import org.kucher.socialservice.service.dto.friendrequest.UpdateFriendRequestDTO;
import org.kucher.socialservice.service.dto.post.ResponsePostDTO;
import org.kucher.socialservice.service.dto.post.UpdatePostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/friend")
public class FriendRequestController {

    private final IFriendRequestService service;

    public FriendRequestController(IFriendRequestService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseFriendRequestDTO> doPost(@RequestBody CreateFriendRequestDTO dto) {

        ResponseFriendRequestDTO created = service.create(dto);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @PatchMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<ResponseFriendRequestDTO> doUpdate(@RequestBody UpdateFriendRequestDTO dto, @PathVariable("uuid") UUID uuid, @PathVariable("dt_update") LocalDateTime dtUpdate) {

        ResponseFriendRequestDTO created = service.update(dto, uuid, dtUpdate);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }
}
