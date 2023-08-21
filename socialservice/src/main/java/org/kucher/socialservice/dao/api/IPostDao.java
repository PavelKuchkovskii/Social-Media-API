package org.kucher.socialservice.dao.api;

import org.kucher.socialservice.dao.entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

public interface IPostDao extends MongoRepository<Post, UUID> {

}
