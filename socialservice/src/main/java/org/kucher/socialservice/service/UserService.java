package org.kucher.socialservice.service;

import org.kucher.socialservice.dao.entity.User;
import org.kucher.socialservice.service.utill.CustomResponseErrorHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class UserService {

    private final RestTemplate restTemplate;
    private final CustomResponseErrorHandler responseErrorHandler;

    public UserService(RestTemplate restTemplate, CustomResponseErrorHandler responseErrorHandler) {
        this.restTemplate = restTemplate;
        this.responseErrorHandler = responseErrorHandler;
    }

    public User getUserByUuid(UUID userUuid) {
        String userUrl = "http://localhost:8080/users/{userUuid}";

        restTemplate.setErrorHandler(responseErrorHandler);

        return restTemplate.getForEntity(userUrl, User.class, userUuid).getBody();
    }
}
