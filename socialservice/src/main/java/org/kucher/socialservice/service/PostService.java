package org.kucher.socialservice.service;

import org.kucher.socialservice.config.utill.Time.TimeUtil;
import org.kucher.socialservice.dao.api.IPostDao;
import org.kucher.socialservice.dao.entity.Post;
import org.kucher.socialservice.dao.entity.User;
import org.kucher.socialservice.dao.entity.builder.PostBuilder;
import org.kucher.socialservice.service.api.IPostService;
import org.kucher.socialservice.service.dto.CreatePostDTO;
import org.kucher.socialservice.service.dto.PostDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService implements IPostService {

    private final IPostDao dao;
    private final RestTemplate restTemplate;

    public PostService(IPostDao dao, RestTemplate restTemplate) {
        this.dao = dao;
        this.restTemplate = restTemplate;
    }

    @Override
    public PostDTO create(CreatePostDTO dto) {

        CreatePostDTO created = new CreatePostDTO();
        created.setUuid(UUID.randomUUID());
        created.setDtCreate(TimeUtil.now());
        created.setDtUpdate(created.getDtCreate());
        created.setUserUuid(dto.getUserUuid());
        created.setText(dto.getText());
        created.setTitle(dto.getTitle());
        created.setImageBase64(dto.getImageBase64());


        if (validate(created)) {
            Post post = mapToEntity(created);
            dao.save(post);
        }

        return read(created.getUuid());
    }

    @Override
    public PostDTO read(UUID uuid) {
        Optional<Post> optional = dao.findById(uuid);
        if(optional.isPresent()) {
            return mapToDTO(optional.get());
        }
        else {
            throw new RuntimeException("Post not found");
        }
    }

    @Override
    public PostDTO update(CreatePostDTO dto, UUID uuid, LocalDateTime dtUpdate) {
        Optional<Post> optional = dao.findById(uuid);
        if(optional.isPresent()) {
            Post post = optional.get();

            if(dtUpdate.isEqual(post.getDtUpdate())) {

                dto.setUuid(post.getUuid());
                dto.setDtCreate(post.getDtCreate());
                dto.setDtUpdate(TimeUtil.now());
                dto.setUserUuid(post.getUserUuid());

                dao.save(mapToEntity(dto));

                return read(post.getUuid());
            }
            else {
                throw new RuntimeException("Post already update");
            }
        }
        else {
            throw new RuntimeException("Post not found");
        }

    }

    @Override
    public boolean delete(UUID uuid) {
        Optional<Post> optional = dao.findById(uuid);

        if(optional.isPresent()) {
            dao.deleteById(uuid);
            return true;
        }
        else {
            throw new RuntimeException("Post not found");
        }
    }

    @Override
    public boolean validate(CreatePostDTO dto) {
        return true;
    }

    @Override
    public Post mapToEntity(CreatePostDTO dto) {
        return PostBuilder
                .create()
                .setUuid(dto.getUuid())
                .setDtCreate(dto.getDtCreate())
                .setDtUpdate(dto.getDtUpdate())
                .setUserUuid(dto.getUserUuid())
                .setText(dto.getText())
                .setTitle(dto.getTitle())
                .setImage(Base64.getDecoder().decode(dto.getImageBase64())) //Decode image from base64 to byte[]
                .build();
    }

    @Override
    public PostDTO mapToDTO(Post post) {
        PostDTO dto = new PostDTO();

        dto.setUuid(post.getUuid());
        dto.setDtCreate(post.getDtCreate());
        dto.setDtUpdate(post.getDtUpdate());
        dto.setUser(getUserByUuid(post.getUserUuid()).getBody());
        dto.setText(post.getText());
        dto.setTitle(post.getTitle());
        dto.setImageBase64(Base64.getEncoder().encodeToString(post.getImage())); //Encode image from byte[] to base64

        return dto;
    }

    @Override
    public ResponseEntity<User> getUserByUuid(UUID userUuid) {
        String userUrl = "http://localhost:8080/users/{userUuid}";

        return restTemplate.getForEntity(userUrl, User.class, userUuid);
    }


}
