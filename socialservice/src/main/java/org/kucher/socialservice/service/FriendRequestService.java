package org.kucher.socialservice.service;

import org.kucher.socialservice.config.utill.Time.TimeUtil;
import org.kucher.socialservice.dao.api.IFriendRequestDao;
import org.kucher.socialservice.dao.entity.FriendRequest;
import org.kucher.socialservice.dao.entity.builder.FriendRequestBuilder;
import org.kucher.socialservice.dao.entity.enums.EFriendRequestStatus;
import org.kucher.socialservice.service.api.IFriendRequestService;
import org.kucher.socialservice.service.dto.friendrequest.FriendRequestDTO;
import org.kucher.socialservice.service.dto.friendrequest.CreateFriendRequestDTO;
import org.kucher.socialservice.service.dto.friendrequest.ResponseFriendRequestDTO;
import org.kucher.socialservice.service.dto.friendrequest.UpdateFriendRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class FriendRequestService implements IFriendRequestService{

    private final IFriendRequestDao dao;
    private final UserService userService;
    private final SubscriptionService subscriptionService;
    private final FriendshipService friendshipService;

    public FriendRequestService(IFriendRequestDao dao, UserService userService, SubscriptionService subscriptionService, FriendshipService friendshipService) {
        this.dao = dao;
        this.userService = userService;
        this.subscriptionService = subscriptionService;
        this.friendshipService = friendshipService;
    }

    @Override
    @Transactional
    public ResponseFriendRequestDTO create(CreateFriendRequestDTO dto) {

        UUID sender = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
        friendRequestDTO.setUuid(UUID.randomUUID());
        friendRequestDTO.setDtCreate(TimeUtil.now());
        friendRequestDTO.setDtUpdate(friendRequestDTO.getDtCreate());
        friendRequestDTO.setSenderUuid(sender);
        friendRequestDTO.setReceiverUuid(dto.getReceiverUuid());
        friendRequestDTO.setStatus(EFriendRequestStatus.WAITING);

        if (validate(friendRequestDTO)) {

            //Create subscription
            subscriptionService.create(sender, friendRequestDTO.getReceiverUuid());

            FriendRequest friendRequest = mapToEntity(friendRequestDTO);
            dao.save(friendRequest);
        }

        return read(friendRequestDTO.getUuid());
    }

    @Override
    public ResponseFriendRequestDTO read(UUID uuid) {
        Optional<FriendRequest> optional = dao.findById(uuid);
        if(optional.isPresent()) {
            return mapToDTO(optional.get());
        }
        else {
            throw new RuntimeException("FriendRequest not found");
        }
    }
    @Override
    public Page<ResponseFriendRequestDTO> read(UUID uuid, int page, int itemsPerPage) {
        Pageable pageable = PageRequest.of(page, itemsPerPage);

        Page<FriendRequest> friendRequests = dao.findAllByReceiverUuidAndStatus(uuid, EFriendRequestStatus.WAITING, pageable);

        return new PageImpl<>(friendRequests.get().map(this::mapToDTO)
                .collect(Collectors.toList()), pageable, friendRequests.getTotalElements());
    }

    @Override
    @Transactional
    public ResponseFriendRequestDTO update(UpdateFriendRequestDTO dto, UUID uuid, LocalDateTime dtUpdate) {
        Optional<FriendRequest> optional = dao.findById(uuid);
        if(optional.isPresent()) {
            FriendRequest friendRequest = optional.get();

            if(dtUpdate.isEqual(friendRequest.getDtUpdate())) {

                //If FriendRequest accepted create subscription
                if(EFriendRequestStatus.get(dto.getStatus()).equals(EFriendRequestStatus.ACCEPTED)) {
                    //Create subscription
                    subscriptionService.create(friendRequest.getReceiverUuid(), friendRequest.getSenderUuid());
                    friendshipService.create(friendRequest);
                }

                FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
                friendRequestDTO.setUuid(uuid);
                friendRequestDTO.setDtCreate(friendRequest.getDtCreate());
                friendRequestDTO.setDtUpdate(TimeUtil.now());
                friendRequestDTO.setSenderUuid(friendRequest.getSenderUuid());
                friendRequestDTO.setReceiverUuid(friendRequest.getReceiverUuid());
                friendRequestDTO.setStatus(EFriendRequestStatus.get(dto.getStatus()));

                dao.save(mapToEntity(friendRequestDTO));

                return read(friendRequest.getUuid());
            }
            else {
                throw new RuntimeException("FriendRequest already updated");
            }
        }
        else {
            throw new RuntimeException("FriendRequest not found");
        }
    }

    @Override
    @Transactional
    public boolean delete(UUID uuid) {
        Optional<FriendRequest> optional = dao.findById(uuid);

        if(optional.isPresent()) {
            dao.deleteById(uuid);
            return true;
        }
        else {
            throw new RuntimeException("FriendRequest not found");
        }
    }

    @Override
    public boolean validate(FriendRequestDTO dto) {
        return true;
    }

    @Override
    public FriendRequest mapToEntity(FriendRequestDTO dto) {

        return FriendRequestBuilder
                .create()
                .setUuid(dto.getUuid())
                .setDtCreate(dto.getDtCreate())
                .setDtUpdate(dto.getDtUpdate())
                .setSender(dto.getSenderUuid())
                .setReceiver(dto.getReceiverUuid())
                .setStatus(dto.getStatus())
                .build();
    }

    @Override
    public ResponseFriendRequestDTO mapToDTO(FriendRequest friendRequest) {
        ResponseFriendRequestDTO dto = new ResponseFriendRequestDTO();

        dto.setUuid(friendRequest.getUuid());
        dto.setDtCreate(friendRequest.getDtCreate());
        dto.setDtUpdate(friendRequest.getDtUpdate());
        dto.setSender(userService.getUserByUuid(friendRequest.getSenderUuid()));
        dto.setReceiver(userService.getUserByUuid(friendRequest.getReceiverUuid()));
        dto.setStatus(friendRequest.getStatus().name());
        return dto;
    }
}
