package com.example.menbosa.dto.senior.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Data
@Component
public class TestResultDTO {
//            INSERT INTO MBS_TEST_RESULT(TEST_RESULT_NUM, TEST_RESULT_DATE, TEST_RESULT_SCORE, SEN_MEM_NUM, TEST_LIST_NUM)
//        VALUES(#{testResultNum}, #{testResultDate}, SYSDATE, #{testResultScore}, #{senMemNum}, #{testListNum})
    private Long testResultNum;
    private LocalDate testResultDate;
    private int testResultScore;
    private Long senMemNum;
    private Long testListNum;
}
