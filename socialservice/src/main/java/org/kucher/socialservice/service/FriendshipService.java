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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
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

    public Page<ResponseFriendshipDTO> read(UUID uuid, int page, int itemsPerPage) {

        Pageable pageable = PageRequest.of(page, itemsPerPage);

        Page<Friendship> friendships = dao.findAllByUserUuid(uuid, pageable);

        return new PageImpl<>(friendships.get().map(this::mapToDTO)
                .collect(Collectors.toList()), pageable, friendships.getTotalElements());
    }

    public boolean delete(UUID uuid) {
        Optional<Friendship> optional = dao.findById(uuid);

        if(optional.isPresent()) {

            UUID muuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

            if(muuid.equals(optional.get().getUser1Uuid()) || muuid.equals(optional.get().getUser2Uuid()) ) {
                dao.deleteById(uuid);
                return true;
            }
            else {
                throw new AccessDeniedException("Access denied");
            }
        }
        else {
            throw new RuntimeException("Friendship not found");
        }
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
