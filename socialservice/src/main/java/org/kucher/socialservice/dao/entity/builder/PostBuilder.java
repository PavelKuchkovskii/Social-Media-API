package org.kucher.socialservice.dao.entity.builder;

import org.kucher.socialservice.dao.entity.Post;

import java.time.LocalDateTime;
import java.util.UUID;

public class PostBuilder {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private UUID userUuid;
    private String text;
    private String title;
    private byte[] image;

    private PostBuilder(){

    }
    public static PostBuilder create() {
        return new PostBuilder();
    }

    public PostBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public PostBuilder setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public PostBuilder setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public PostBuilder setUserUuid(UUID userUuid) {
        this.userUuid = userUuid;
        return this;
    }

    public PostBuilder setText(String text) {
        this.text = text;
        return this;
    }

    public PostBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public PostBuilder setImage(byte[] image) {
        this.image = image;
        return this;
    }

    public Post build() {
        return new Post(uuid, dtCreate, dtUpdate, userUuid,text,title,image);
    }
}
