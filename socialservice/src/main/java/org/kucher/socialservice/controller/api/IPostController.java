package org.kucher.socialservice.controller.api;


import io.swagger.annotations.*;
import org.kucher.socialservice.dto.message.Message;
import org.kucher.socialservice.dto.post.CreatePostDTO;
import org.kucher.socialservice.dto.post.ResponsePostDTO;
import org.kucher.socialservice.dto.post.UpdatePostDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@Api(tags = "Posts")
public interface IPostController {

    @ApiOperation("Create a post")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Post created successfully", response = ResponsePostDTO.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    ResponseEntity<ResponsePostDTO> createPost(
            @ApiParam(value = "Post data", required = true) @RequestBody @Valid CreatePostDTO dto);

    @ApiOperation("Get a post by UUID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Post retrieved successfully", response = ResponsePostDTO.class),
            @ApiResponse(code = 404, message = "Post not found")
    })
    ResponseEntity<ResponsePostDTO> getPostByUuid(
            @ApiParam(value = "UUID of the post", required = true) @PathVariable("uuid") UUID uuid);

    @ApiOperation("Get all posts by user UUID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Posts retrieved successfully", response = Page.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    ResponseEntity<Page<ResponsePostDTO>> getAllPostByUserUuid(
            @ApiParam(value = "UUID of the user", required = true) @PathVariable("uuid") UUID uuid,
            @ApiParam(value = "Page number", required = true, example = "1") @RequestParam int page,
            @ApiParam(value = "Number of items per page", required = true, example = "10") @RequestParam int size);

    @ApiOperation("Update a post")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Post updated successfully", response = ResponsePostDTO.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Post not found")
    })
    ResponseEntity<ResponsePostDTO> updatePost(
            @ApiParam(value = "Post data", required = true) @RequestBody @Valid UpdatePostDTO dto,
            @ApiParam(value = "UUID of the post", required = true) @PathVariable("uuid") UUID uuid,
            @ApiParam(value = "Date and time of the post update", required = true) @PathVariable("dt_update") LocalDateTime dtUpdate);

    @ApiOperation("Delete a post")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Post deleted successfully"),
            @ApiResponse(code = 404, message = "Post not found")
    })
    ResponseEntity<Message> deletePost(
            @ApiParam(value = "UUID of the post", required = true) @PathVariable("uuid") UUID uuid);
}
