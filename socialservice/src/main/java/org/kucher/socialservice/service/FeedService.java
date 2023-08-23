package org.kucher.socialservice.service;

import org.kucher.socialservice.service.dto.post.ResponsePostDTO;
import org.kucher.socialservice.service.dto.subscription.ResponseSubscriptionDTO;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FeedService {

    private final SubscriptionService subscriptionService;
    private final PostService postService;

    public FeedService(SubscriptionService subscriptionService, PostService postService) {
        this.subscriptionService = subscriptionService;
        this.postService = postService;
    }


    public Page<ResponsePostDTO> read(UUID uuid, int page, int itemsPerPage, int sort) {
        UUID muuid= UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getName());

        if(muuid.equals(uuid)) {
            Pageable pageable;

            if (sort == 0) {
                pageable = PageRequest.of(page, itemsPerPage);
            } else if (sort == 1) {
                pageable = PageRequest.of(page, itemsPerPage, Sort.by(Sort.Direction.ASC));
            } else {
                pageable = PageRequest.of(page, itemsPerPage, Sort.by(Sort.Direction.DESC));
            }

            List<ResponseSubscriptionDTO> subscriptions;

            subscriptions = subscriptionService.read(uuid, false);

            List<ResponsePostDTO> postDTOS = new ArrayList<>();

            for (ResponseSubscriptionDTO subscription : subscriptions) {
                List<ResponsePostDTO> topPosts = postService.get(subscription.getFollowedUser().getUuid());
                postDTOS.addAll(topPosts.subList(0, Math.min(5, topPosts.size())));
            }

            return new PageImpl<>(postDTOS, pageable, postDTOS.size());
        }
        else {
            throw new AccessDeniedException("Access denied");
        }
    }

}
