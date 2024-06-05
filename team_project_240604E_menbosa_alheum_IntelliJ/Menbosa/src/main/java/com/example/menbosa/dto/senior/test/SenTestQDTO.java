package com.example.menbosa.dto.senior.test;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class SenTestQDTO {
    private Long testQuestNum;
    private String testQuestOrder;
    private String testQuestImg;
    private String testQuestAnswer;
}
