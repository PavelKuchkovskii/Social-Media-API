package org.kucher.socialservice.controller;

import org.kucher.socialservice.service.SubscriptionService;
import org.kucher.socialservice.service.dto.subscription.ResponseSubscriptionDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/subscription")
public class SubscriptionController {

    private final SubscriptionService service;

    public SubscriptionController(SubscriptionService service) {
        this.service = service;
    }

    @GetMapping("/follower")
    public ResponseEntity<Page<ResponseSubscriptionDTO>> getFollowers(@RequestParam int page, @RequestParam int size) {

        UUID uuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        Page<ResponseSubscriptionDTO> subscriptionDTOS = this.service.read(uuid, true, page, size);

        return new ResponseEntity<>(subscriptionDTOS, HttpStatus.OK);
    }

    @GetMapping("/followed")
    public ResponseEntity<Page<ResponseSubscriptionDTO>> getFollowedUsers(@RequestParam int page, @RequestParam int size) {

        UUID uuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        Page<ResponseSubscriptionDTO> subscriptionDTOS = this.service.read(uuid, false, page, size);

        return new ResponseEntity<>(subscriptionDTOS, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> doDelete(@PathVariable("uuid")UUID uuid) {

        service.delete(uuid);

        return new ResponseEntity<>("Subscription has been rejected", HttpStatus.OK);
    }


}
