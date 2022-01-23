package com.ddd.tooda.domain.user.service;

import com.ddd.tooda.domain.user.model.SocialName;
import com.ddd.tooda.domain.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    private final String socialId = "wwlkfjwlekjfwef";

    @Test
    void signUpOrLogin() {
        User user1 = userService.signUpOrLogin(SocialName.APPLE, socialId);
        User user2 = userService.signUpOrLogin(SocialName.APPLE, socialId);
        assertEquals(user1.getId(),user2.getId());
    }

    @Test
    void deleteUser() {
        User user = userService.signUpOrLogin(SocialName.APPLE, socialId);
        userService.deleteUser(user.getId());
        User user2 = userService.signUpOrLogin(SocialName.APPLE, socialId);
        assertNotEquals(user.getId(),user2.getId());
    }
}