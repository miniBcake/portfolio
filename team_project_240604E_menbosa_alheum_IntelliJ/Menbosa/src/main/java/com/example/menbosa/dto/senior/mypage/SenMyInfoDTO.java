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
public class SenMyInfoDTO {
//    <select id="selectSenMyInfo" resultType="SenMyInfoDTO" parameterType="Long">
//            -- 시니어 마이페이지 어르신정보
//    SELECT SEN_MEM_NAME, SEN_MEM_BIRTH, SEN_MEM_PHONE_MID, SEN_MEM_PHONE_BACK, SEN_MEM_PASSWORD
//    FROM MBS_SEN_MEMBER
//    WHERE SEN_MEM_NUM = #{senMemNum}
//    </select>
    private Long senMemNum;
    private String senMemName;
    private LocalDate senMemBirth;
//    private LocalDate senMemYear;
//    private LocalDate senMemMonth;
//    private LocalDate senMemDay;
    private int senMemPhoneMid;
    private int senMemPhoneBack;
    private String senMemPhone;
}
