package org.kucher.socialservice.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.kucher.socialservice.dto.message.Message;
import org.kucher.socialservice.dto.post.CreatePostDTO;
import org.kucher.socialservice.dto.post.ResponsePostDTO;
import org.kucher.socialservice.dto.post.UpdatePostDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@Api(value = "Post API provides the capability to  controller", tags = {"post_controller"})
public interface IPostController {
    @ApiOperation(value = "Creating a new post.", notes = "Creates a new post in the system and save in database.", tags = {"create_post",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post created successfully.")})
    @RequestMapping(
            produces = {"text/plain"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<ResponsePostDTO> createPost(
            @ApiParam(
                    value = "A JSON value representing a post.",
                    example = "{foo: whatever, bar: whatever2}")
            @Valid @RequestBody CreatePostDTO dto);

    @ApiOperation(value = "Receiving a post by uuid.", notes = "Receiving a post by uuid from database.", tags = {"find_post",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post receiving successfully."),
            @ApiResponse(code = 404, message = "Post not found.")})
    @RequestMapping(
            produces = {"text/plain"},
            consumes = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<ResponsePostDTO> getPostByUuid(
            @ApiParam(
                    value = "A UUID value representing a saved post.",
                    example = "{foo: whatever, bar: whatever2}")
            @PathVariable("uuid") UUID uuid);

    @ApiOperation(value = "Updating an existed post.", notes = "Updates an existed post in database.", tags = {"update_post",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post updated successfully.")})
    @RequestMapping(
            produces = {"text/plain"},
            consumes = {"application/json"},
            method = RequestMethod.PATCH)
    ResponseEntity<ResponsePostDTO> updatePost(
            @ApiParam(
                    value = "A UUID value representing a saved post.",
                    example = "{foo: whatever, bar: whatever2}")
            @RequestBody UpdatePostDTO dto,
            @PathVariable("uuid") UUID uuid,
            @PathVariable("dt_update") LocalDateTime dtUpdate);


    @ApiOperation(value = "Deleting an existed post.", notes = "Deletes an existed post in database.", tags = {"delete_post",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post deleted successfully."),
            @ApiResponse(code = 404, message = "Post not found.")})
    @RequestMapping(
            produces = {"text/plain"},
            consumes = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<Message> deletePost(@ApiParam(
            value = "A UUID value representing a saved post.",
            example = "{foo: whatever, bar: whatever2}")
                                    @PathVariable("uuid") UUID uuid);

}
