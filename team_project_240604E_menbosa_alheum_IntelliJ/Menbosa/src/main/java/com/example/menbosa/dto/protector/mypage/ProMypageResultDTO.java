package com.example.menbosa.dto.protector.mypage;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class ProMypageResultDTO {
    private LocalDateTime resultSurveyDate;
    private String resultSurveyScore;
}
