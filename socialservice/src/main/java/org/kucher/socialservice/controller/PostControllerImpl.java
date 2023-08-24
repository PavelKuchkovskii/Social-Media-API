package org.kucher.socialservice.controller;

import org.kucher.socialservice.controller.api.IPostController;
import org.kucher.socialservice.dto.message.Message;
import org.kucher.socialservice.service.api.IPostService;
import org.kucher.socialservice.dto.post.CreatePostDTO;
import org.kucher.socialservice.dto.post.ResponsePostDTO;
import org.kucher.socialservice.dto.post.UpdatePostDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/posts")
public class PostControllerImpl implements IPostController {

    private final IPostService service;

    public PostControllerImpl(IPostService service) {
        this.service = service;
    }

    @Override
    @PostMapping
    public ResponseEntity<ResponsePostDTO> createPost(@Valid @RequestBody CreatePostDTO dto) {

        ResponsePostDTO created = service.create(dto);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @Override
    @GetMapping("/{uuid}")
    public ResponseEntity<ResponsePostDTO> getPostByUuid(@PathVariable("uuid") UUID uuid) {

        ResponsePostDTO read = service.read(uuid);

        return new ResponseEntity<>(read, HttpStatus.OK);
    }

    @GetMapping("/user/{uuid}")
    public ResponseEntity<Page<ResponsePostDTO>> getAllPostByUserUuid(@PathVariable("uuid") UUID uuid, @RequestParam("page") int page, @RequestParam("size") int size) {

        Page<ResponsePostDTO> read = service.findAllByUserUuid(uuid, page, size);

        return new ResponseEntity<>(read, HttpStatus.OK);
    }

    @Override
    @PatchMapping("/{uuid}/dt_update/{dt_update}")
    public ResponseEntity<ResponsePostDTO> updatePost(@Valid @RequestBody UpdatePostDTO dto, @PathVariable("uuid") UUID uuid, @PathVariable("dt_update") LocalDateTime dtUpdate) {

        ResponsePostDTO created = service.update(dto, uuid, dtUpdate);

        return new ResponseEntity<>(created, HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/{uuid}")
    public ResponseEntity<Message> deletePost(@PathVariable("uuid") UUID uuid) {

        service.delete(uuid);

        return new ResponseEntity<>(new Message("info", "Post has been deleted"), HttpStatus.OK);
    }
}
