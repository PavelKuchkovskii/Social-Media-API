package org.kucher.socialservice.model;

import org.kucher.socialservice.model.api.IPost;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "posts")
public class Post implements IPost {

    @Id
    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private UUID userUuid;
    private String text;
    private String title;
    private byte[] image;

    public Post() {
    }

    public Post(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtUpdate, UUID userUuid, String text, String title, byte[] image) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.userUuid = userUuid;
        this.text = text;
        this.title = title;
        this.image = image;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    public LocalDateTime getDtCreate() {
        return this.dtCreate;
    }

    @Override
    public LocalDateTime getDtUpdate() {
        return this.dtUpdate;
    }

    @Override
    public UUID getUserUuid() {
        return this.userUuid;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public byte[] getImage() {
        return this.image;
    }
}
