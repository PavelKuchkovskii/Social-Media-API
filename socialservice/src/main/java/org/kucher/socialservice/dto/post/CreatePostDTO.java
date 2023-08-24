package org.kucher.socialservice.dto.post;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class CreatePostDTO {
    @NotNull(message = "User uuid cannot be blank")
    private UUID userUuid;
    @NotBlank(message = "Text cannot be blank")
    private String text;
    @NotBlank(message = "Title cannot be blank")
    private String title;
    private String imageBase64;

    public UUID getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageBase64() {
        return imageBase64;
    }

    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
}
