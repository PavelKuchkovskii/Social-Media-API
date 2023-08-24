package org.kucher.socialservice.controller.api;

import io.swagger.annotations.*;
import org.kucher.socialservice.dto.friendhip.ResponseFriendshipDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "Friendships")
public interface IFriendShipController {

    @ApiOperation("Get a page of user's friends")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User's friends retrieved successfully", response = Page.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    ResponseEntity<Page<ResponseFriendshipDTO>> getFriends(
            @ApiParam(value = "Page number", required = true, example = "1") @RequestParam int page,
            @ApiParam(value = "Number of items per page", required = true, example = "10") @RequestParam int size);
}
