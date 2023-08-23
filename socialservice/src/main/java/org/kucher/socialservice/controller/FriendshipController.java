package org.kucher.socialservice.controller;

import org.kucher.socialservice.service.FriendshipService;
import org.kucher.socialservice.service.dto.friendhip.ResponseFriendshipDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/friend")
public class FriendshipController {

    private final FriendshipService service;

    public FriendshipController(FriendshipService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ResponseFriendshipDTO>> getFriends(@RequestParam int page, @RequestParam int size) {

        UUID uuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        Page<ResponseFriendshipDTO> responseFriendshipDTOS = this.service.read(uuid, page, size);

        return new ResponseEntity<>(responseFriendshipDTOS, HttpStatus.OK);
    }
}
