package com.example.menbosa.service.senior.user;

import com.example.menbosa.dto.senior.user.SenUserDTO;
import com.example.menbosa.mapper.senior.user.SenUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SenUserServiceImplTest {

    @Autowired
    SenUserMapper senUserMapper;
    @Autowired
    SenUserDTO senUserDTO;

    @Test
    void insertSenUser() {
        senUserDTO.setSenMemName("장서혁");
        senUserDTO.setSenMemPhoneMid(1234);
        senUserDTO.setSenMemPhoneBack(5679);
        senUserDTO.setSenMemCertification(12341);
        senUserDTO.setSenMemPassword("qqqq");
        senUserDTO.setSenMemPasswordVerify("qqqq");

        senUserMapper.insertSenUser(senUserDTO);

        System.out.println(senUserDTO);
    }
}