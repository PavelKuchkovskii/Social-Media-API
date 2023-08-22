package org.kucher.socialservice.service.api;

import org.kucher.socialservice.dao.entity.Post;
import org.kucher.socialservice.service.dto.post.PostDTO;
import org.kucher.socialservice.service.dto.post.CreatePostDTO;
import org.kucher.socialservice.service.dto.post.ResponsePostDTO;
import org.kucher.socialservice.service.dto.post.UpdatePostDTO;

public interface IPostService extends IService<CreatePostDTO, UpdatePostDTO, ResponsePostDTO, PostDTO, Post> {

}
