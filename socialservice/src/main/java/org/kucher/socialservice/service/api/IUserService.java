package org.kucher.socialservice.service.api;

import org.kucher.socialservice.model.User;

import java.util.UUID;

/**
 * Interface for a service providing user-related operations.
 */
public interface IUserService {

    /**
     * Retrieves a user based on the provided UUID.
     *
     * @param userUuid The UUID of the user to retrieve.
     * @return The User object associated with the provided UUID.
     */
    User getUserByUuid(UUID userUuid);
}
