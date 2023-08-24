package org.kucher.socialservice.service;

import org.kucher.socialservice.exception.crud.UserNotFoundException;
import org.kucher.socialservice.exception.rest.RestApiException;
import org.kucher.socialservice.model.User;
import org.kucher.socialservice.service.api.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

/**
 * Service implementation for managing user-related operations.
 */
@Service
public class UserServiceImpl implements IUserService {

    private final RestTemplate restTemplate;

    public UserServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Retrieves user information by their UUID using an external REST API.
     *
     * @param userUuid The UUID of the user to retrieve information for.
     * @return The user information retrieved from the external API.
     * @throws UserNotFoundException If the user with the specified UUID is not found.
     * @throws RestApiException If there is an issue with the external REST API.
     */
    @Override
    public User getUserByUuid(UUID userUuid) throws UserNotFoundException, RestApiException {
        String userUrl = "http://localhost:8080/users/{userUuid}";

        ResponseEntity<User> responseEntity = restTemplate.getForEntity(userUrl, User.class, userUuid);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }
        else if (responseEntity.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
            throw new UserNotFoundException("User with UUID " + userUuid + " not found.");
        }
        else {
            throw new RestApiException("Error while accessing the external REST API.");
        }
    }
}
