package org.kucher.userservice.controller.api;

import io.swagger.annotations.*;
import org.kucher.userservice.dto.UserCreateDTO;
import org.kucher.userservice.dto.UserLoginDTO;
import org.kucher.userservice.dto.message.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api(tags = "Authentication")
public interface IAuthController {

    @ApiOperation("Register a new user")
    @ApiResponses({
            @ApiResponse(code = 201, message = "User registered successfully"),
            @ApiResponse(code = 400, message = "Invalid input data"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    ResponseEntity<Message> registerUser(
            @ApiParam(value = "User registration data", required = true) @Valid @RequestBody UserCreateDTO dto);

    @ApiOperation("Authenticate user")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User authenticated successfully"),
            @ApiResponse(code = 400, message = "Invalid input data"),
            @ApiResponse(code = 401, message = "Authentication failed"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    ResponseEntity<Message> loginUser(
            @ApiParam(value = "User login data", required = true) @Valid @RequestBody UserLoginDTO dto);
}
