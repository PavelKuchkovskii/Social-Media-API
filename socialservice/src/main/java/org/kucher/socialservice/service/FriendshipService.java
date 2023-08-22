package org.kucher.socialservice.service;

import org.kucher.socialservice.dao.api.IFriendshipDao;
import org.kucher.socialservice.dao.entity.FriendRequest;
import org.kucher.socialservice.dao.entity.Friendship;
import org.kucher.socialservice.dao.entity.builder.FriendshipBuilder;
import org.kucher.socialservice.service.dto.friendhip.FriendshipDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class FriendshipService {

    private final IFriendshipDao dao;
    private final UserService userService;

    public FriendshipService(IFriendshipDao dao, UserService userService) {
        this.dao = dao;
        this.userService = userService;
    }

    @Transactional
    public FriendshipDTO create(FriendRequest friendRequest) {

        FriendshipDTO friendshipDTO = new FriendshipDTO();
        friendshipDTO.setUuid(UUID.randomUUID());
        friendshipDTO.setUser1Uuid(friendRequest.getSenderUuid());
        friendshipDTO.setUser2Uuid(friendRequest.getReceiverUuid());


        if (validate(friendshipDTO)) {
            Friendship friendship = mapToEntity(friendshipDTO);
            dao.save(friendship);
        }

        return friendshipDTO;
    }

    public boolean validate(FriendshipDTO dto) {
        return true;
    }

    public Friendship mapToEntity(FriendshipDTO dto) {
        return FriendshipBuilder
                .create()
                .setUuid(dto.getUuid())
                .setUser1Uuid(dto.getUser1Uuid())
                .setUser2Uuid(dto.getUser2Uuid())
                .build();
    }
}
