package com.example.menbosa.service.protector.survey;

import com.example.menbosa.dto.protector.survey.ProSurveyQDTO;

import java.util.List;

public interface SurveyService {
    List<ProSurveyQDTO> selectSurveyQ();
    void insertSurvey(Long resultSurveyScore, Long proMemNum);
}
