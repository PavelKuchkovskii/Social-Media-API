package org.kucher.socialservice.controller.api;

import io.swagger.annotations.*;
import org.kucher.socialservice.dto.message.Message;
import org.kucher.socialservice.dto.subscription.CreateSubscriptionDTO;
import org.kucher.socialservice.dto.subscription.ResponseSubscriptionDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.UUID;

@Api(tags = "Subscriptions")
public interface ISubscriptionController {

    @ApiOperation("Create a subscription")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Subscription created successfully", response = ResponseSubscriptionDTO.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    ResponseEntity<ResponseSubscriptionDTO> createSubscription(
            @ApiParam(value = "Subscription data", required = true) @RequestBody @Valid CreateSubscriptionDTO dto);

    @ApiOperation("Get a page of followers")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Followers retrieved successfully", response = Page.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    ResponseEntity<Page<ResponseSubscriptionDTO>> getFollowers(
            @ApiParam(value = "Page number", required = true, example = "1") @RequestParam int page,
            @ApiParam(value = "Number of items per page", required = true, example = "10") @RequestParam int size);

    @ApiOperation("Get a page of followed users")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Followed users retrieved successfully", response = Page.class),
            @ApiResponse(code = 400, message = "Bad request")
    })
    ResponseEntity<Page<ResponseSubscriptionDTO>> getFollowedUsers(
            @ApiParam(value = "Page number", required = true, example = "1") @RequestParam int page,
            @ApiParam(value = "Number of items per page", required = true, example = "10") @RequestParam int size);

    @ApiOperation("Delete a subscription")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Subscription deleted successfully"),
            @ApiResponse(code = 404, message = "Subscription not found")
    })
    ResponseEntity<Message> deleteSubscription(
            @ApiParam(value = "UUID of the subscription", required = true) @PathVariable("uuid") UUID uuid);
}
