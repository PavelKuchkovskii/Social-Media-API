package org.kucher.userservice.dao.api;

import org.junit.jupiter.api.Test;
import org.kucher.userservice.TestApplication;
import org.kucher.userservice.dao.entity.User;
import org.kucher.userservice.dao.entity.enums.EUserRole;
import org.kucher.userservice.dao.entity.enums.EUserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@SpringJUnitConfig(TestApplication.class)
@TestPropertySource(properties = {"spring.profiles.active=test"})
class UserDaoTest {
    @Autowired
    private IUserDao dao;

    @Test
    void findByEmail() {
        String email = "test@mail.com";
        User user = new User(UUID.fromString("9da9952a-10e4-47c1-86d3-89843ba217f7"),
                LocalDateTime.parse("2023-01-01T00:00:00"),
                LocalDateTime.parse("2023-01-01T00:00:00"),
                email,
                "testName",
                "testSurname",
                "testPassword",
                EUserRole.ROLE_USER,
                EUserStatus.ACTIVATED);

        dao.save(user);

        User expected = new User(UUID.fromString("9da9952a-10e4-47c1-86d3-89843ba217f7"),
                LocalDateTime.parse("2023-01-01T00:00:00"),
                LocalDateTime.parse("2023-01-01T00:00:00"),
                "test@mail.com",
                "testName",
                "testSurname",
                "testPassword",
                EUserRole.ROLE_USER,
                EUserStatus.ACTIVATED);

        Optional<User> actual = dao.findByEmail(email);
        assertEquals(expected, actual.orElse(null));

        dao.delete(user);
    }

}