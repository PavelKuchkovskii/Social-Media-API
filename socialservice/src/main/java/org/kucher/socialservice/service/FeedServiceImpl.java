package org.kucher.socialservice.service;

import org.kucher.socialservice.dto.post.ResponsePostDTO;
import org.kucher.socialservice.dto.subscription.ResponseSubscriptionDTO;
import org.kucher.socialservice.service.api.IFeedService;
import org.kucher.socialservice.service.api.IPostService;
import org.kucher.socialservice.service.api.ISubscriptionService;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 *
 * Implementing the ISubscriptionService interface for managing the feed
 *
 */
@Service
public class FeedServiceImpl implements IFeedService {

    private final ISubscriptionService subscriptionService;
    private final IPostService postService;

    public FeedServiceImpl(ISubscriptionService subscriptionService, IPostService postService) {
        this.subscriptionService = subscriptionService;
        this.postService = postService;
    }

    /**
     * Retrieves a paginated list of top posts for the authenticated user's feed.
     *
     * @param uuid The UUID of the authenticated user.
     * @param page The page number for pagination (0-indexed).
     * @param itemsPerPage The number of items per page.
     * @param sort The sorting option for the feed:
     *             - 0: No sorting
     *             - 1: Ascending order
     *             - 2: Descending order
     * @return A paginated list of top posts for the authenticated user's feed.
     * @throws AccessDeniedException if the provided UUID doesn't match the authenticated user's UUID.
     */
    @Override
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
