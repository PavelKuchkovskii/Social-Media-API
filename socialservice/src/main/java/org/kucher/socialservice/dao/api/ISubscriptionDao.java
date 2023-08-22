package org.kucher.socialservice.dao.api;

import org.kucher.socialservice.dao.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ISubscriptionDao extends JpaRepository<Subscription, UUID> {
}
