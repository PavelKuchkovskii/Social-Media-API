package org.kucher.socialservice.controller;

import org.kucher.socialservice.service.api.IPostService;
import org.kucher.socialservice.service.dto.PostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController {

    private IPostService service;

    public PostController(IPostService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PostDTO> doPost(@RequestBody PostDTO dto) {

        PostDTO created = service.create(dto);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PostDTO> doGet(@PathVariable("uuid")UUID uuid) {

        PostDTO read = service.read(uuid);

        return new ResponseEntity<>(read, HttpStatus.OK);
    }

    @PatchMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<PostDTO> doUpdate(@RequestBody PostDTO dto,@PathVariable("uuid")UUID uuid, @PathVariable("dt_update")LocalDateTime dtUpdate) {

        PostDTO created = service.update(dto, uuid, dtUpdate);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> doDelete(@PathVariable("uuid")UUID uuid) {

        service.delete(uuid);

        return new ResponseEntity<>("Post has been deleted", HttpStatus.OK);
    }
}
