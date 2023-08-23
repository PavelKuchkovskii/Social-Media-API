package org.kucher.socialservice.dao.api;

import org.kucher.socialservice.dao.entity.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ISubscriptionDao extends JpaRepository<Subscription, UUID> {

    Page<Subscription> findAllByFollowerUuid(UUID uuid, Pageable pageable);
    Page<Subscription> findAllByFollowedUserUuid(UUID uuid, Pageable pageable);
    List<Subscription> findAllByFollowerUuid(UUID uuid);
    List<Subscription> findAllByFollowedUserUuid(UUID uuid);
}
