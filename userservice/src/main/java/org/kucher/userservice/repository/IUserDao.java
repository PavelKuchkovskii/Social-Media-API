package org.kucher.userservice.repository;


import org.kucher.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing User entities.
 */
public interface IUserDao extends JpaRepository<User, UUID> {

    /**
     * Retrieves a User entity associated with the given email.
     *
     * @param email The email of the user.
     * @return An optional containing the User entity associated with the given email, or empty if not found.
     */
    Optional<User> findByEmail(String email);
}
