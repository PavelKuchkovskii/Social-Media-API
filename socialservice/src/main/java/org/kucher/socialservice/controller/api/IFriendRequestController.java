package org.kucher.socialservice.controller.api;

import io.swagger.annotations.*;
import org.kucher.socialservice.dto.friendrequest.ResponseFriendRequestDTO;
import org.kucher.socialservice.dto.friendrequest.UpdateFriendRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@Api(tags = "Friend Requests")
public interface IFriendRequestController {

    @ApiOperation("Update a friend request")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Friend request updated successfully", response = ResponseFriendRequestDTO.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Friend request not found")
    })
    ResponseEntity<ResponseFriendRequestDTO> updateFriendRequest(
            @ApiParam(value = "Friend request update data", required = true) @RequestBody @Valid UpdateFriendRequestDTO dto,
            @ApiParam(value = "UUID of the friend request", required = true) @PathVariable("uuid") UUID uuid,
            @ApiParam(value = "Date and time of the friend request update", required = true) @PathVariable("dt_update") LocalDateTime dtUpdate);

    @ApiOperation("Get a page of friend requests")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Friend requests retrieved successfully", response = Page.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    ResponseEntity<Page<ResponseFriendRequestDTO>> getPageFriendRequest(
            @ApiParam(value = "Page number", required = true, example = "1") @RequestParam int page,
            @ApiParam(value = "Number of items per page", required = true, example = "10") @RequestParam int size);
}
