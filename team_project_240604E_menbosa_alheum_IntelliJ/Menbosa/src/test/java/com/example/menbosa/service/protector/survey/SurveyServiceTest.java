package com.example.menbosa.service.protector.survey;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SurveyServiceTest {

    @Autowired
    private SurveyService surveyService;

    @Test
    void insertSurvey() {
        surveyService.insertSurvey(20L,600L);
    }
}