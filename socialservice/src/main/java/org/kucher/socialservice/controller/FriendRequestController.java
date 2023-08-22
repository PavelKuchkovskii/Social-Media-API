package org.kucher.socialservice.controller;

import org.kucher.socialservice.service.api.IFriendRequestService;
import org.kucher.socialservice.service.dto.friendrequest.CreateFriendRequestDTO;
import org.kucher.socialservice.service.dto.friendrequest.ResponseFriendRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
