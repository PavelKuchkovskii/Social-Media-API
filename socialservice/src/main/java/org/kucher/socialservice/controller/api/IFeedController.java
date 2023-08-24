package org.kucher.socialservice.controller.api;

import io.swagger.annotations.*;
import org.kucher.socialservice.dto.post.ResponsePostDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "Feed Controller", description = "Operations related to the feed")
public interface IFeedController {

    @ApiOperation(value = "Get the feed", notes = "Retrieves a paginated feed of posts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Feed retrieved successfully"),
            @ApiResponse(code = 400, message = "Invalid request parameters"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    ResponseEntity<Page<ResponsePostDTO>> getFeedPage(
            @ApiParam(value = "Page number", example = "1", required = true) @RequestParam int page,
            @ApiParam(value = "Number of items per page", example = "10", required = true) @RequestParam int size,
            @ApiParam(value = "Sorting order (0 for ascending, 1 for descending)", example = "0", required = true) @RequestParam int sort
    );
}
