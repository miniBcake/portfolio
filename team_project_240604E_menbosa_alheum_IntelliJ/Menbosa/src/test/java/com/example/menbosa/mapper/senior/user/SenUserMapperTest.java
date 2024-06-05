package com.example.menbosa.mapper.senior.user;

import com.example.menbosa.dto.senior.user.SenUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class SenUserMapperTest {

    @Autowired
    SenUserMapper senUserMapper;
    @Autowired
    SenUserDTO senUserDTO;

    @Test
    void insertSenUser() {
//        senUserDTO.setSenMemNum(500);
        senUserDTO.setSenMemName("장서혁");
        senUserDTO.setSenMemPhoneMid(1233);
        senUserDTO.setSenMemPhoneBack(5648);
        senUserDTO.setSenMemCertification(12341);
        senUserDTO.setSenMemPassword("qqqq");
        senUserDTO.setSenMemPasswordVerify("qqqq");

        senUserMapper.insertSenUser(senUserDTO);

    }
}