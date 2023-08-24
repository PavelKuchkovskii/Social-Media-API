package org.kucher.socialservice.service;

import org.kucher.socialservice.event.FriendshipAcceptedEvent;
import org.kucher.socialservice.service.api.IUserService;
import org.kucher.socialservice.utill.Time.TimeUtil;
import org.kucher.socialservice.repository.IFriendRequestRepository;
import org.kucher.socialservice.model.FriendRequest;
import org.kucher.socialservice.model.Subscription;
import org.kucher.socialservice.model.builder.FriendRequestBuilder;
import org.kucher.socialservice.model.enums.EFriendRequestStatus;
import org.kucher.socialservice.service.api.IFriendRequestService;
import org.kucher.socialservice.dto.friendrequest.FriendRequestDTO;
import org.kucher.socialservice.dto.friendrequest.CreateFriendRequestDTO;
import org.kucher.socialservice.dto.friendrequest.ResponseFriendRequestDTO;
import org.kucher.socialservice.dto.friendrequest.UpdateFriendRequestDTO;
import org.kucher.socialservice.dto.subscription.SubscriptionDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link IFriendRequestService} interface for managing friend request-related operations.
 */
@Service
@Transactional(readOnly = true)
public class FriendRequestServiceImpl implements IFriendRequestService {

    private final IFriendRequestRepository dao;
    private final IUserService userService;
    private final ApplicationEventPublisher eventPublisher;

    public FriendRequestServiceImpl(IFriendRequestRepository dao, IUserService userService, ApplicationEventPublisher eventPublisher) {
        this.dao = dao;
        this.userService = userService;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Creates a new friend request based on the provided information.
     *
     * @param dto The DTO containing details for creating the friend request.
     * @return The DTO representing the created friend request.
     * @throws IllegalArgumentException If the authenticated user attempts to send a friend request to themselves.
     * @throws RuntimeException If an issue occurs during the friend request creation process.
     */
    @Override
    @Transactional
    public ResponseFriendRequestDTO create(CreateFriendRequestDTO dto) {

        UUID sender = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        if(!sender.equals(dto.getReceiverUuid())) {

            FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
            friendRequestDTO.setUuid(UUID.randomUUID());
            friendRequestDTO.setDtCreate(TimeUtil.now());
            friendRequestDTO.setDtUpdate(friendRequestDTO.getDtCreate());
            friendRequestDTO.setSenderUuid(sender);
            friendRequestDTO.setReceiverUuid(dto.getReceiverUuid());
            friendRequestDTO.setStatus(EFriendRequestStatus.WAITING);


            if (validate(friendRequestDTO)) {
                FriendRequest friendRequest = mapToEntity(friendRequestDTO);
                dao.save(friendRequest);

                return mapToDTO(friendRequest);
            }
            else {
                throw new RuntimeException("Something was wrong. Try again later");
            }


        }
        else {
            throw new IllegalArgumentException("Unable to perform operation");
        }
    }


    /**
     * Retrieves the details of a friend request based on the provided UUID.
     *
     * @param uuid The UUID of the friend request to retrieve.
     * @return The DTO representing the friend request details.
     * @throws AccessDeniedException If the authenticated user does not have access to view the friend request.
     * @throws RuntimeException If the requested friend request is not found.
     */
    @Override
    public ResponseFriendRequestDTO read(UUID uuid) {
        UUID muuid= UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        Optional<FriendRequest> optional = dao.findById(uuid);
        if(optional.isPresent()) {
            if(muuid.equals(optional.get().getSenderUuid()) || muuid.equals(optional.get().getReceiverUuid())) {

                return mapToDTO(optional.get());
            }
            else {
                throw new AccessDeniedException("Access denied");
            }
        }
        else {
            throw new RuntimeException("FriendRequest not found");
        }
    }

    /**
     * Retrieves a paginated list of friend requests that are pending and sent to the authenticated user.
     *
     * @param uuid The UUID of the authenticated user.
     * @param page The page number for pagination (0-indexed).
     * @param itemsPerPage The number of items per page.
     * @return A paginated list of pending friend requests for the authenticated user.
     * @throws AccessDeniedException If the provided UUID does not match the authenticated user's UUID.
     */
    @Override
    public Page<ResponseFriendRequestDTO> read(UUID uuid, int page, int itemsPerPage) {
        UUID muuid= UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        if(uuid.equals(muuid)) {

            Pageable pageable = PageRequest.of(page, itemsPerPage);

            Page<FriendRequest> friendRequests = dao.findAllByReceiverUuidAndStatus(uuid, EFriendRequestStatus.WAITING, pageable);

            return new PageImpl<>(friendRequests.get().map(this::mapToDTO)
                    .collect(Collectors.toList()), pageable, friendRequests.getTotalElements());
        }
        else {
            throw new AccessDeniedException("Access denied");
        }
    }

    /**
     * Updates a friend request based on information from a subscription DTO.
     *
     * @param subscriptionDTO The DTO containing subscription details.
     * @throws RuntimeException If an issue occurs during the friend request update process or if the related friend request is not found.
     */
    @Override
    public void updateFromSubscriptionService(SubscriptionDTO subscriptionDTO) {
        Optional<FriendRequest> optional = dao.findBySenderUuidAndReceiverUuid(subscriptionDTO.getFollowedUserUuid(), subscriptionDTO.getFollowerUuid());

        if (optional.isPresent()) {
            ResponseFriendRequestDTO dto = mapToDTO(optional.get());

            FriendRequestDTO friendRequestDTO = new FriendRequestDTO();
            friendRequestDTO.setUuid(dto.getUuid());
            friendRequestDTO.setDtCreate(dto.getDtCreate());
            friendRequestDTO.setDtUpdate(TimeUtil.now());
            friendRequestDTO.setSenderUuid(dto.getSender().getUuid());
            friendRequestDTO.setReceiverUuid(dto.getReceiver().getUuid());
            friendRequestDTO.setStatus(EFriendRequestStatus.ACCEPTED);

            dao.save(mapToEntity(friendRequestDTO));
        } else {
            throw new RuntimeException("Friend request not found");
        }
    }

    /**
     * Updates the details of a friend request based on the provided information.
     *
     * @param dto The DTO containing updated friend request details.
     * @param uuid The UUID of the friend request to update.
     * @param dtUpdate The datetime of the last update of the friend request.
     * @return The DTO representing the updated friend request details.
     * @throws AccessDeniedException If the authenticated user is not the receiver of the friend request.
     * @throws RuntimeException If the friend request has already been updated or if the friend request is not found.
     */
    @Override
    @Transactional
    public ResponseFriendRequestDTO update(UpdateFriendRequestDTO dto, UUID uuid, LocalDateTime dtUpdate) {
        Optional<FriendRequest> optional = dao.findById(uuid);
        if(optional.isPresent()) {
            FriendRequest friendRequest = optional.get();

            UUID muuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

            if(muuid.equals(friendRequest.getReceiverUuid())) {

                if (dtUpdate.isEqual(friendRequest.getDtUpdate())) {

                    //If FriendRequest accepted create subscription
                    if (EFriendRequestStatus.get(dto.getStatus()).equals(EFriendRequestStatus.ACCEPTED)) {
                        //Create subscription
                        eventPublisher.publishEvent(new FriendshipAcceptedEvent(friendRequest));
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
                throw new AccessDeniedException("Access denied");
            }

        }
        else {
            throw new RuntimeException("FriendRequest not found");
        }
    }

    /**
     * Deletes a friend request based on the provided subscription details.
     *
     * @param subscription The subscription containing follower and followed user UUIDs.
     * @throws RuntimeException If an issue occurs during the friend request deletion process or if the related friend request is not found.
     */
    @Override
    public void deleteFromSubscriptionService(Subscription subscription) {
        Optional<FriendRequest> request1 = dao.findBySenderUuidAndReceiverUuid(subscription.getFollowerUuid(), subscription.getFollowedUserUuid());

        if(request1.isEmpty()) {
            Optional<FriendRequest> request2 = dao.findBySenderUuidAndReceiverUuid(subscription.getFollowedUserUuid(), subscription.getFollowerUuid());

            if(request2.isEmpty()) {
                throw new RuntimeException();
            }
            else {
                dao.deleteById(request2.get().getUuid());
            }
        }
        else {
            dao.deleteById(request1.get().getUuid());
        }
    }

    /**
     * Deletes a friend request with the provided UUID.
     *
     * @param uuid The UUID of the friend request to be deleted.
     * @return True if the friend request is successfully deleted, false otherwise.
     * @throws AccessDeniedException If the authenticated user is not the sender of the friend request.
     * @throws RuntimeException If the friend request is not found.
     */
    @Override
    @Transactional
    public boolean delete(UUID uuid) {
        Optional<FriendRequest> optional = dao.findById(uuid);

        if(optional.isPresent()) {

            UUID userUuid = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

            if(userUuid.equals(optional.get().getSenderUuid())) {
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
