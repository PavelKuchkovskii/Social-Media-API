package org.kucher.socialservice.service;

import org.kucher.socialservice.service.dto.post.ResponsePostDTO;
import org.kucher.socialservice.service.dto.subscription.ResponseSubscriptionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    public Page<ResponsePostDTO> read(UUID uuid, int page, int itemsPerPage) {
        Pageable pageable = PageRequest.of(page, itemsPerPage);

        List<ResponseSubscriptionDTO> subscriptions;

        subscriptions = subscriptionService.read(uuid, false);

        List<ResponsePostDTO> postDTOS = new ArrayList<>();

        for (ResponseSubscriptionDTO subscription : subscriptions) {
            List<ResponsePostDTO> topPosts = postService.get(subscription.getFollowedUser().getUuid());
            postDTOS.addAll(topPosts.subList(0, Math.min(5, topPosts.size())));
        }

        return new PageImpl<>(postDTOS, pageable, postDTOS.size());
    }

}
