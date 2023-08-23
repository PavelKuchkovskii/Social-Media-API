package org.kucher.socialservice.service;

import org.kucher.socialservice.dao.api.IFriendshipDao;
import org.kucher.socialservice.dao.entity.FriendRequest;
import org.kucher.socialservice.dao.entity.Friendship;
import org.kucher.socialservice.dao.entity.builder.FriendshipBuilder;
import org.kucher.socialservice.service.dto.friendhip.FriendshipDTO;
import org.kucher.socialservice.service.dto.friendhip.ResponseFriendshipDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FriendshipService {

    private final IFriendshipDao dao;
    private final UserService userService;

    public FriendshipService(IFriendshipDao dao, UserService userService) {
        this.dao = dao;
        this.userService = userService;
    }

    @Transactional
    public ResponseFriendshipDTO create(FriendRequest friendRequest) {

        FriendshipDTO friendshipDTO = new FriendshipDTO();
        friendshipDTO.setUuid(UUID.randomUUID());
        friendshipDTO.setUser1Uuid(friendRequest.getSenderUuid());
        friendshipDTO.setUser2Uuid(friendRequest.getReceiverUuid());


        if (validate(friendshipDTO)) {
            Friendship friendship = mapToEntity(friendshipDTO);
            dao.save(friendship);
        }

        ///!!!!!!!!!!!
        return null;
    }

    public Page<ResponseFriendshipDTO> read(int page, int itemsPerPage) {
        Pageable pageable = PageRequest.of(page, itemsPerPage);

        Page<Friendship> journalFoods = dao.findAllByUserUuid(UUID.randomUUID(), pageable);

        return new PageImpl<>(journalFoods.get().map(this::mapToDTO)
                .collect(Collectors.toList()), pageable, journalFoods.getTotalElements());
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

    public ResponseFriendshipDTO mapToDTO(Friendship entity) {
        ResponseFriendshipDTO responseFriendshipDTO = new ResponseFriendshipDTO();
        responseFriendshipDTO.setUuid(entity.getUuid());
        responseFriendshipDTO.setUser1(userService.getUserByUuid(entity.getUser1Uuid()));
        responseFriendshipDTO.setUser2(userService.getUserByUuid(entity.getUser2Uuid()));

        return responseFriendshipDTO;
    }
}