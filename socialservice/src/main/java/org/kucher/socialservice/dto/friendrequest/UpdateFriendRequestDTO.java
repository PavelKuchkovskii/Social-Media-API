package org.kucher.socialservice.dto.friendrequest;

import javax.validation.constraints.NotBlank;

public class UpdateFriendRequestDTO {
    @NotBlank(message = "Status cannot be blank")
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
