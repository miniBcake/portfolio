package com.example.menbosa.dto.protector.survey;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ProSurveyQDTO {
    private long surveyQuestNum;
    private String surveyQuestContents;
}
