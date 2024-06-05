package com.example.menbosa.service.protector.user;

import com.example.menbosa.mapper.protector.user.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    private UserService userService;

    @Test
    void loginUser() {
        System.out.println(userService.loginUser("78903423", "567"));
    }
}