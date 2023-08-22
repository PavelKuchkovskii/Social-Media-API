package org.kucher.socialservice.service;

import org.kucher.socialservice.config.utill.Time.TimeUtil;
import org.kucher.socialservice.dao.api.IPostDao;
import org.kucher.socialservice.dao.entity.Post;
import org.kucher.socialservice.dao.entity.builder.PostBuilder;
import org.kucher.socialservice.service.api.IPostService;
import org.kucher.socialservice.service.dto.post.PostDTO;
import org.kucher.socialservice.service.dto.post.CreatePostDTO;
import org.kucher.socialservice.service.dto.post.ResponsePostDTO;
import org.kucher.socialservice.service.dto.post.UpdatePostDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class PostService implements IPostService {
    private final IPostDao dao;
    private final UserService userService;

    public PostService(IPostDao dao, UserService userService) {
        this.dao = dao;
        this.userService = userService;
    }


    //Нужно проверить есть ли такой юзер
    @Override
    public ResponsePostDTO create(CreatePostDTO dto) {

        PostDTO postDTO = new PostDTO();
        postDTO.setUuid(UUID.randomUUID());
        postDTO.setDtCreate(TimeUtil.now());
        postDTO.setDtUpdate(postDTO.getDtCreate());
        postDTO.setUserUuid(dto.getUserUuid());
        postDTO.setText(dto.getText());
        postDTO.setTitle(dto.getTitle());
        postDTO.setImageBase64(dto.getImageBase64());


        if (validate(postDTO)) {
            Post post = mapToEntity(postDTO);
            dao.save(post);
        }

        return read(postDTO.getUuid());
    }

    @Override
    public ResponsePostDTO read(UUID uuid) {
        Optional<Post> optional = dao.findById(uuid);
        if(optional.isPresent()) {
            return mapToDTO(optional.get());
        }
        else {
            throw new RuntimeException("Post not found");
        }
    }

    @Override
    public ResponsePostDTO update(UpdatePostDTO dto, UUID uuid, LocalDateTime dtUpdate) {
        Optional<Post> optional = dao.findById(uuid);
        if(optional.isPresent()) {
            Post post = optional.get();

            if(dtUpdate.isEqual(post.getDtUpdate())) {

                PostDTO postDTO = new PostDTO();
                postDTO.setUuid(uuid);
                postDTO.setDtCreate(post.getDtCreate());
                postDTO.setDtUpdate(TimeUtil.now());
                postDTO.setUserUuid(post.getUserUuid());
                postDTO.setText(dto.getText());
                postDTO.setTitle(dto.getTitle());
                postDTO.setImageBase64(dto.getImageBase64());

                dao.save(mapToEntity(postDTO));

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
    public ResponsePostDTO mapToDTO(Post post) {
        ResponsePostDTO dto = new ResponsePostDTO();

        dto.setUuid(post.getUuid());
        dto.setDtCreate(post.getDtCreate());
        dto.setDtUpdate(post.getDtUpdate());
        dto.setUser(userService.getUserByUuid(post.getUserUuid()));
        dto.setText(post.getText());
        dto.setTitle(post.getTitle());
        dto.setImageBase64(Base64.getEncoder().encodeToString(post.getImage())); //Encode image from byte[] to base64

        return dto;
    }

}
