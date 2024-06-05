package com.example.menbosa.dto.senior.mypage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SenMyProInfoDTO {

    private Long senMemNum;
    private String proMemName;
    private int proMemPhoneMid;
    private int proMemPhoneBack;
    private String proMemPhone;
}
