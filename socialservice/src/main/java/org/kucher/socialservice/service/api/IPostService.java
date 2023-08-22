package org.kucher.socialservice.service.api;

import org.kucher.socialservice.dao.entity.Post;
import org.kucher.socialservice.dao.entity.User;
import org.kucher.socialservice.service.dto.CreatePostDTO;
import org.kucher.socialservice.service.dto.PostDTO;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface IPostService extends IService<CreatePostDTO, PostDTO, Post> {

    ResponseEntity<User> getUserByUuid(UUID uuid);
}
