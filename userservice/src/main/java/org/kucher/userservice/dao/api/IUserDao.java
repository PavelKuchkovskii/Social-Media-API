package org.kucher.userservice.dao.api;


import org.kucher.userservice.dao.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUserDao extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
}
