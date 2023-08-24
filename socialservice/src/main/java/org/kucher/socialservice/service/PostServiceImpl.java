package org.kucher.socialservice.service;

import org.kucher.socialservice.service.api.IUserService;
import org.kucher.socialservice.utill.Time.TimeUtil;
import org.kucher.socialservice.repository.IPostRepository;
import org.kucher.socialservice.model.Post;
import org.kucher.socialservice.model.User;
import org.kucher.socialservice.model.builder.PostBuilder;
import org.kucher.socialservice.service.api.IPostService;
import org.kucher.socialservice.dto.post.PostDTO;
import org.kucher.socialservice.dto.post.CreatePostDTO;
import org.kucher.socialservice.dto.post.ResponsePostDTO;
import org.kucher.socialservice.dto.post.UpdatePostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link IPostService} interface providing operations related to posts.
 */
@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements IPostService {
    private final IPostRepository dao;
    private final IUserService userService;

    public PostServiceImpl(IPostRepository dao, IUserService userService) {
        this.dao = dao;
        this.userService = userService;
    }

    /**
     * Creates a new post based on the provided DTO and associates it with the authenticated user.
     *
     * @param dto The DTO containing post information.
     * @return A {@link ResponsePostDTO} representing the created post.
     */
    @Override
    @Transactional
    public ResponsePostDTO create(CreatePostDTO dto) {
        UUID userUuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        User user = userService.getUserByUuid(userUuid);

        PostDTO postDTO = new PostDTO();
        postDTO.setUuid(UUID.randomUUID());
        postDTO.setDtCreate(TimeUtil.now());
        postDTO.setDtUpdate(postDTO.getDtCreate());
        postDTO.setUserUuid(user.getUuid());
        postDTO.setText(dto.getText());
        postDTO.setTitle(dto.getTitle());
        postDTO.setImageBase64(dto.getImageBase64());


        if (validate(postDTO)) {
            Post post = mapToEntity(postDTO);
            dao.save(post);
        }

        return read(postDTO.getUuid());
    }

    /**
     * Retrieves the details of a post with the given UUID.
     *
     * @param uuid The UUID of the post to retrieve.
     * @return A {@link ResponsePostDTO} representing the details of the retrieved post.
     * @throws EntityNotFoundException If no post with the specified UUID is found.
     */
    @Override
    public ResponsePostDTO read(UUID uuid) {
        Optional<Post> optional = dao.findById(uuid);
        if(optional.isPresent()) {
            return mapToDTO(optional.get());
        }
        else {
            throw new EntityNotFoundException("Post not found");
        }
    }


    /**
     * Updates the details of a post with the given UUID.
     *
     * @param dto The {@link UpdatePostDTO} containing the updated post details.
     * @param uuid The UUID of the post to update.
     * @param dtUpdate The timestamp indicating the last update time of the post.
     * @return A {@link ResponsePostDTO} representing the updated details of the post.
     * @throws EntityNotFoundException If no post with the specified UUID is found.
     * @throws AccessDeniedException If the current user does not have access to update the post.
     * @throws RuntimeException If the provided last update timestamp does not match the actual last update time of the post.
     */
    @Override
    @Transactional
    public ResponsePostDTO update(UpdatePostDTO dto, UUID uuid, LocalDateTime dtUpdate) {
        Optional<Post> optional = dao.findById(uuid);

        if(optional.isPresent()) {
            Post post = optional.get();

            UUID userUuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

            if(userUuid.equals(post.getUserUuid())) {

                if (dtUpdate.isEqual(post.getDtUpdate())) {

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
                throw new AccessDeniedException("Access denied");
            }
        }
        else {
            throw new RuntimeException("Post not found");
        }

    }

    /**
     * Deletes a post with the specified UUID.
     *
     * @param uuid The UUID of the post to delete.
     * @return {@code true} if the post is successfully deleted, {@code false} otherwise.
     * @throws EntityNotFoundException If no post with the specified UUID is found.
     * @throws AccessDeniedException If the current user does not have access to delete the post.
     * @throws RuntimeException If an unexpected error occurs while deleting the post.
     */
    @Override
    @Transactional
    public boolean delete(UUID uuid) {
        Optional<Post> optional = dao.findById(uuid);

        if(optional.isPresent()) {

            UUID userUuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

            if(userUuid.equals(optional.get().getUserUuid())) {
                dao.deleteById(uuid);
                return true;
            }
            else {
                throw new AccessDeniedException("Access denied");
            }
        }
        else {
            throw new RuntimeException("Post not found");
        }
    }

    /**
     * Retrieves a list of top posts for the user with the specified UUID.
     *
     * @param uuid The UUID of the user for whom to retrieve top posts.
     * @return A list of top posts for the specified user.
     */
    @Override
    public List<ResponsePostDTO> get(UUID uuid) {
        return dao.findAllByUserUuid(uuid).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    /**
     * Retrieves a paginated list of posts created by the user with the specified UUID.
     *
     * @param uuid The UUID of the user for whom to retrieve posts.
     * @param page The page number to retrieve (zero-based).
     * @param itemsPerPage The number of items per page.
     * @return A paginated list of posts created by the specified user.
     */
    @Override
    public Page<ResponsePostDTO> findAllByUserUuid(UUID uuid, int page, int itemsPerPage) {
        Pageable pageable = PageRequest.of(page, itemsPerPage);

        Page<Post> posts = dao.findAllByUserUuid(uuid, pageable);

        return new PageImpl<>(posts.get().map(this::mapToDTO)
                .collect(Collectors.toList()), pageable, posts.getTotalElements());
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
