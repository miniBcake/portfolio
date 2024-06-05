package com.example.menbosa.controller.protector.user;

import com.example.menbosa.dto.protector.user.UserDTO;
import com.example.menbosa.mapper.protector.user.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void join() {
        UserDTO userDTO = new UserDTO();

        userDTO.setProMemNum(2L);
        userDTO.setProMemName("김영선");
        userDTO.setProMemPhoneMid(3456);
        userDTO.setProMemPhoneBack(7890);
        userDTO.setProMemCertification(3423);
        userDTO.setProMemPassword("567");
        userDTO.setProMemPasswordVerify("567");
        userDTO.setProMemEmail("333@333.com");

        userMapper.insertUser(userDTO);
        System.out.println(userMapper);
        System.out.println(userDTO);
    }
}