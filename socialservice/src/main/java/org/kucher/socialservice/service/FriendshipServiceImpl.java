package org.kucher.socialservice.service;

import org.kucher.socialservice.repository.IFriendshipRepository;
import org.kucher.socialservice.model.FriendRequest;
import org.kucher.socialservice.model.Friendship;
import org.kucher.socialservice.model.Subscription;
import org.kucher.socialservice.model.builder.FriendshipBuilder;
import org.kucher.socialservice.dto.friendhip.FriendshipDTO;
import org.kucher.socialservice.dto.friendhip.ResponseFriendshipDTO;
import org.kucher.socialservice.service.api.IFriendshipService;
import org.kucher.socialservice.service.api.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link IFriendshipService} interface providing operations related to friendships.
 */
@Service
public class FriendshipServiceImpl implements IFriendshipService {

    private final IFriendshipRepository dao;
    private final IUserService userService;

    public FriendshipServiceImpl(IFriendshipRepository dao, IUserService userService) {
        this.dao = dao;
        this.userService = userService;
    }

    /**
     * Creates a new friendship based on the provided friend request details.
     *
     * @param friendRequest The friend request containing sender and receiver UUIDs.
     * @return True if the friendship is successfully created, false otherwise.
     * @throws IllegalArgumentException If the operation cannot be performed due to invalid parameters.
     */
    @Override
    @Transactional
    public boolean create(FriendRequest friendRequest) {
        UUID muuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        if(!friendRequest.getSenderUuid().equals(friendRequest.getReceiverUuid())
                && (muuid.equals(friendRequest.getSenderUuid()) || muuid.equals(friendRequest.getReceiverUuid()) )) {

            FriendshipDTO friendshipDTO = new FriendshipDTO();
            friendshipDTO.setUuid(UUID.randomUUID());
            friendshipDTO.setUser1Uuid(friendRequest.getSenderUuid());
            friendshipDTO.setUser2Uuid(friendRequest.getReceiverUuid());


            if (validate(friendshipDTO)) {
                Friendship friendship = mapToEntity(friendshipDTO);
                dao.save(friendship);
            }

            return true;
        }
        else {
            throw new IllegalArgumentException("Unable to perform operation");
        }
    }

    /**
     * Retrieves a paginated list of friendships associated with the provided user UUID.
     *
     * @param uuid The UUID of the user for whom friendships are to be retrieved.
     * @param page The page number for pagination (0-based).
     * @param itemsPerPage The number of items per page for pagination.
     * @return A {@link Page} of {@link ResponseFriendshipDTO} representing the retrieved friendships.
     */
    @Override
    public Page<ResponseFriendshipDTO> read(UUID uuid, int page, int itemsPerPage) {

        Pageable pageable = PageRequest.of(page, itemsPerPage);

        Page<Friendship> friendships = dao.findAllByUserUuid(uuid, pageable);

        return new PageImpl<>(friendships.get().map(this::mapToDTO)
                .collect(Collectors.toList()), pageable, friendships.getTotalElements());
    }

    /**
     * Deletes a friendship based on the provided subscription details.
     *
     * @param subscription The subscription containing follower and followed user UUIDs.
     */
    @Override
    @Transactional
    public void delete(Subscription subscription) {
        Optional<Friendship> request1 = dao.findByUser1UuidAndUser2Uuid(subscription.getFollowerUuid(), subscription.getFollowedUserUuid());

        if(request1.isEmpty()) {
            Optional<Friendship> request2 = dao.findByUser1UuidAndUser2Uuid(subscription.getFollowedUserUuid(), subscription.getFollowerUuid());

            request2.ifPresent(friendship -> dao.deleteById(friendship.getUuid()));
        }
        else {
            dao.deleteById(request1.get().getUuid());
        }
    }
    @Override
    public boolean validate(FriendshipDTO dto) {
        return true;
    }
    @Override
    public Friendship mapToEntity(FriendshipDTO dto) {
        return FriendshipBuilder
                .create()
                .setUuid(dto.getUuid())
                .setUser1Uuid(dto.getUser1Uuid())
                .setUser2Uuid(dto.getUser2Uuid())
                .build();
    }
    @Override
    public ResponseFriendshipDTO mapToDTO(Friendship entity) {
        ResponseFriendshipDTO responseFriendshipDTO = new ResponseFriendshipDTO();
        responseFriendshipDTO.setUuid(entity.getUuid());
        responseFriendshipDTO.setUser1(userService.getUserByUuid(entity.getUser1Uuid()));
        responseFriendshipDTO.setUser2(userService.getUserByUuid(entity.getUser2Uuid()));

        return responseFriendshipDTO;
    }
}
