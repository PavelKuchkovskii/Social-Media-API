package org.kucher.socialservice.dao.api;

import org.kucher.socialservice.dao.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface IPostDao extends MongoRepository<Post, UUID> {
    List<Post> findTopPostsByUserUuidOrderByDtCreateDesc(UUID uuid);

    Page<Post> findAllByUserUuid(UUID uuid, Pageable pageable);
}
