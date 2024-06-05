package com.example.menbosa.service.senior.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SenTestServiceImplTest {
    @Autowired
    private SenTestService senTestService;

    @Test
    void selectTestItems() {
        System.out.println(senTestService.selectTestItems(600L,600L));
//        dbeaver 에서 확인 나옴
    }
}