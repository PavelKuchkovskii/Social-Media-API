package org.kucher.socialservice.controller;

import org.kucher.socialservice.controller.api.ISubscriptionController;
import org.kucher.socialservice.dto.message.Message;
import org.kucher.socialservice.service.SubscriptionServiceImpl;
import org.kucher.socialservice.dto.subscription.CreateSubscriptionDTO;
import org.kucher.socialservice.dto.subscription.ResponseSubscriptionDTO;
import org.kucher.socialservice.service.api.ISubscriptionService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/subscription")
public class SubscriptionControllerImpl implements ISubscriptionController {

    private final ISubscriptionService service;

    public SubscriptionControllerImpl(SubscriptionServiceImpl service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<ResponseSubscriptionDTO> createSubscription(@Valid @RequestBody CreateSubscriptionDTO dto) {

        ResponseSubscriptionDTO created = service.create(dto);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @Override
    @GetMapping("/follower")
    public ResponseEntity<Page<ResponseSubscriptionDTO>> getFollowers(@RequestParam int page, @RequestParam int size) {

        UUID uuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        Page<ResponseSubscriptionDTO> subscriptionDTOS = this.service.read(uuid, true, page, size);

        return new ResponseEntity<>(subscriptionDTOS, HttpStatus.OK);
    }

    @Override
    @GetMapping("/followed")
    public ResponseEntity<Page<ResponseSubscriptionDTO>> getFollowedUsers(@RequestParam int page, @RequestParam int size) {

        UUID uuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        Page<ResponseSubscriptionDTO> subscriptionDTOS = this.service.read(uuid, false, page, size);

        return new ResponseEntity<>(subscriptionDTOS, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Message> deleteSubscription(@PathVariable("uuid")UUID uuid) {

        service.delete(uuid);

        return new ResponseEntity<>(new Message("info", "Subscription has been rejected"), HttpStatus.OK);
    }


}
