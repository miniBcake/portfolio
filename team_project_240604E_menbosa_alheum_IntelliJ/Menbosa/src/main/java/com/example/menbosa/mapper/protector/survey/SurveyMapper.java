package com.example.menbosa.mapper.protector.survey;

import com.example.menbosa.dto.protector.survey.ProSurveyQDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SurveyMapper {
    List<ProSurveyQDTO> selectSurveyQ();
    void insertSurvey(Long resultSurveyScore, Long proMemNum);
}
