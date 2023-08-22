package org.kucher.socialservice.service;

import org.kucher.socialservice.dao.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class UserService {

    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public User getUserByUuid(UUID userUuid) {
        String userUrl = "http://localhost:8080/users/{userUuid}";

        return restTemplate.getForEntity(userUrl, User.class, userUuid).getBody();
    }
}
