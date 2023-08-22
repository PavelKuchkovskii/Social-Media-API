package org.kucher.socialservice.controller;

import org.kucher.socialservice.service.FriendshipService;
import org.kucher.socialservice.service.dto.friendhip.ResponseFriendshipDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/friend")
public class FriendshipController {

    private final FriendshipService service;

    public FriendshipController(FriendshipService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<ResponseFriendshipDTO>> doGet(@RequestParam int page, @RequestParam int size) {

        Page<ResponseFriendshipDTO> responseFriendshipDTOS = this.service.read(page, size);

        return new ResponseEntity<>(responseFriendshipDTOS, HttpStatus.OK);
    }
}
