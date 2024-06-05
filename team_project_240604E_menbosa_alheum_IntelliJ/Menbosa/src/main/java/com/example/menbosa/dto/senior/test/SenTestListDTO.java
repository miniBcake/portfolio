package com.example.menbosa.dto.senior.test;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SenTestListDTO {
//    SELECT TEST_LIST_NUM
//             , TEST_LIST_NAME
//             , TEST_LIST_DIFFI
//             , TEST_LIST_AGE
//        FROM MBS_TEST_LIST

    private Long testListNum;
    private String testListName;
    private String testListDiffi;
    private String testListAge;
}
