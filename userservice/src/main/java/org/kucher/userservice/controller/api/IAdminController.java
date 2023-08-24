package org.kucher.userservice.controller.api;

import io.swagger.annotations.*;
import org.kucher.userservice.dto.UserByAdminDTO;
import org.kucher.userservice.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;


@Api(tags = "Admin Management")
public interface IAdminController {

    @ApiOperation("Create a new user by admin")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User created successfully"),
            @ApiResponse(code = 400, message = "Invalid input data"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    ResponseEntity<UserDTO> createUserByAdmin(
            @ApiParam(value = "User data", required = true) @Valid @RequestBody UserByAdminDTO dto);

    @ApiOperation("Update user information by admin")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User updated successfully"),
            @ApiResponse(code = 400, message = "Invalid input data"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    ResponseEntity<UserDTO> updateUserByAdmin(
            @ApiParam(value = "User UUID", required = true) @PathVariable("uuid") UUID uuid,
            @ApiParam(value = "Update timestamp", required = true) @PathVariable("dt_update") LocalDateTime dtUpdate,
            @ApiParam(value = "Updated user data", required = true) @Valid @RequestBody UserByAdminDTO dto);
}
