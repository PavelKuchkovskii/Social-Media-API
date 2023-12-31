package org.kucher.socialservice.controller;

import org.kucher.socialservice.controller.api.IFriendShipController;
import org.kucher.socialservice.dto.friendhip.ResponseFriendshipDTO;
import org.kucher.socialservice.service.api.IFriendshipService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/friend")
public class FriendshipControllerImpl implements IFriendShipController {

    private final IFriendshipService service;

    public FriendshipControllerImpl(IFriendshipService service) {
        this.service = service;
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<ResponseFriendshipDTO>> getFriends(@RequestParam int page, @RequestParam int size) {

        UUID uuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        Page<ResponseFriendshipDTO> responseFriendshipDTOS = this.service.read(uuid, page, size);

        return new ResponseEntity<>(responseFriendshipDTOS, HttpStatus.OK);
    }
}
