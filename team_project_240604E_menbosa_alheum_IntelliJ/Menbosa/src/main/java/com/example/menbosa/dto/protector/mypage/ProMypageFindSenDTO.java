package com.example.menbosa.dto.protector.mypage;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ProMypageFindSenDTO {
    private long senMemNum;
    private String senMemName;
    private int senMemPhoneMid;
    private int senMemPhoneBack;
    private String senMemPhoneNum; //전화번호 풀자리 01000000000
    private int senMemCertification;
}
