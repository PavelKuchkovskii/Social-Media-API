package org.kucher.socialservice.service;

import org.kucher.socialservice.config.utill.Time.TimeUtil;
import org.kucher.socialservice.dao.api.IPostDao;
import org.kucher.socialservice.dao.entity.Post;
import org.kucher.socialservice.dao.entity.builder.PostBuilder;
import org.kucher.socialservice.service.api.IPostService;
import org.kucher.socialservice.service.dto.PostDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService implements IPostService {

    private IPostDao dao;

    public PostService(IPostDao dao) {
        this.dao = dao;
    }

    @Override
    public PostDTO create(PostDTO dto) {

        PostDTO created = new PostDTO();
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

        return created;
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
    public PostDTO update(PostDTO dto, UUID uuid, LocalDateTime dtUpdate) {
        Optional<Post> optional = dao.findById(uuid);
        if(optional.isPresent()) {
            PostDTO postDTO = mapToDTO(optional.get());

            if(dtUpdate.isEqual(postDTO.getDtUpdate())) {
                postDTO.setDtUpdate(TimeUtil.now());
                postDTO.setText(dto.getText());
                postDTO.setTitle(dto.getTitle());
                postDTO.setImageBase64(dto.getImageBase64());

                dao.save(mapToEntity(postDTO));

                return postDTO;
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
    public boolean validate(PostDTO dto) {
        return true;
    }

    @Override
    public Post mapToEntity(PostDTO dto) {
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
        dto.setUserUuid(post.getUserUuid());
        dto.setText(post.getText());
        dto.setTitle(post.getTitle());
        dto.setImageBase64(Base64.getEncoder().encodeToString(post.getImage())); //Encode image from byte[] to base64

        return dto;
    }

}
