package com.example.menbosa.service.protector.survey;

import com.example.menbosa.dto.protector.survey.ProSurveyQDTO;
import com.example.menbosa.mapper.protector.survey.SurveyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyServiceimpl implements SurveyService {

    private final SurveyMapper surveyMapper;

    @Override
    public List<ProSurveyQDTO> selectSurveyQ() {
        return surveyMapper.selectSurveyQ();
    }

    @Override
    public void insertSurvey(Long resultSurveyScore, Long proMemNum) {
        surveyMapper.insertSurvey(resultSurveyScore, proMemNum);
    }
}
