package com.example.menbosa.dto.senior.mypage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SenMyTestInfoDTO {
//    <select id="selectSenMyTestInfo" resultType="SenMyTestInfoDTO" parameterType="long">
//        SELECT R.TEST_RESULT_DATE
//             , R.TEST_RESULT_SCORE
//             , L.TEST_LIST_NAME
//        FROM MBS_TEST_RESULT R JOIN MBS_TEST_LIST L
//                                    ON R.TEST_LIST_NUM = L.TEST_LIST_NUM
//        WHERE SEN_MEM_NUM = #{senMemNum}
//        ORDER BY TEST_RESULT_SEQ DESC
//    </select>
    private Long senMemNum;
    private LocalDate testResultDate;
    private int testResultScore;
    private String testListName;
}
