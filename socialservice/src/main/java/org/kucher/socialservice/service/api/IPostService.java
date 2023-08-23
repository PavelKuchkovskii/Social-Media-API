package org.kucher.socialservice.service.api;

import org.kucher.socialservice.dao.entity.Post;
import org.kucher.socialservice.service.dto.post.PostDTO;
import org.kucher.socialservice.service.dto.post.CreatePostDTO;
import org.kucher.socialservice.service.dto.post.ResponsePostDTO;
import org.kucher.socialservice.service.dto.post.UpdatePostDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface IPostService extends IService<CreatePostDTO, UpdatePostDTO, ResponsePostDTO, PostDTO, Post> {

    List<ResponsePostDTO> get(UUID uuid);
    Page<ResponsePostDTO> findAllByUserUuid(UUID uuid, int page, int itemsPerPage);

}
