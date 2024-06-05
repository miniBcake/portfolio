package com.example.menbosa.mapper.protector.user;

import com.example.menbosa.dto.protector.user.UserDTO;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void insertUser() {
        UserDTO userDTO = new UserDTO();

        userDTO.setProMemNum(1L);
        userDTO.setProMemName("양효준");
        userDTO.setProMemPhoneMid(2386);
        userDTO.setProMemPhoneBack(3707);
        userDTO.setProMemCertification(1234);
        userDTO.setProMemPassword("123");
        userDTO.setProMemPasswordVerify("123");
        userDTO.setProMemEmail("123@123.com");

        userMapper.insertUser(userDTO);
        System.out.println(userMapper);
        System.out.println(userDTO);
        System.out.println(userDTO.getProMemNum());

    }

    @Test
    void testInsertUser() {
    }

    @Test
    void selectId() {
    }

    @Test
    void selectUserInfo() {
    }
}