package org.kucher.socialservice.controller;

import org.kucher.socialservice.service.api.IPostService;
import org.kucher.socialservice.service.dto.post.CreatePostDTO;
import org.kucher.socialservice.service.dto.post.ResponsePostDTO;
import org.kucher.socialservice.service.dto.post.UpdatePostDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostController implements IPostController {

    private final IPostService service;

    public PostController(IPostService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<ResponsePostDTO> doPost(@RequestBody CreatePostDTO dto) {

        ResponsePostDTO created = service.create(dto);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{uuid}")
    public ResponseEntity<ResponsePostDTO> doGet(@PathVariable("uuid") UUID uuid) {

        ResponsePostDTO read = service.read(uuid);

        return new ResponseEntity<>(read, HttpStatus.OK);
    }

    @Override
    @PatchMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<ResponsePostDTO> doUpdate(@RequestBody UpdatePostDTO dto, @PathVariable("uuid") UUID uuid, @PathVariable("dt_update") LocalDateTime dtUpdate) {

        ResponsePostDTO created = service.update(dto, uuid, dtUpdate);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{uuid}")
    public ResponseEntity<String> doDelete(@PathVariable("uuid") UUID uuid) {

        service.delete(uuid);

        return new ResponseEntity<>("Post has been deleted", HttpStatus.OK);
    }
}
