package org.kucher.userservice.controller.api;

import io.swagger.annotations.*;
import org.kucher.userservice.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Api(tags = "User")
public interface IUserController {

    @ApiOperation("Get current user's information")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User information retrieved successfully"),
            @ApiResponse(code = 401, message = "Not authenticated"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    ResponseEntity<UserDTO> getMe();

    @ApiOperation("Get user information by UUID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User information retrieved successfully"),
            @ApiResponse(code = 400, message = "Invalid UUID format"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    ResponseEntity<UserDTO> getUserByUuid(
            @ApiParam(value = "UUID of the user", required = true) @PathVariable("uuid") UUID uuid);
}