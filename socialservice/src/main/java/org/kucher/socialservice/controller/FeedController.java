package org.kucher.socialservice.controller;

import org.kucher.socialservice.dto.post.ResponsePostDTO;
import org.kucher.socialservice.service.api.IFeedService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/feed")
public class FeedController {

    private final IFeedService service;

    public FeedController(IFeedService service) {
        this.service = service;
    }

    //SORT:
    //0-none
    //1-asc !->
    //2-desc <-!
    @GetMapping
    public ResponseEntity<Page<ResponsePostDTO>> doGet(@RequestParam int page, @RequestParam int size, @RequestParam int sort) {

        UUID uuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        Page<ResponsePostDTO> postDTOS = this.service.read(uuid, page, size, sort);

        return new ResponseEntity<>(postDTOS, HttpStatus.OK);
    }
}
