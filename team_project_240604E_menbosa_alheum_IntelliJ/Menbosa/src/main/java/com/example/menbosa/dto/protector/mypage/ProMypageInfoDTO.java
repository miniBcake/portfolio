package com.example.menbosa.dto.protector.mypage;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ProMypageInfoDTO {
    private String proMemName;
    private String proMemEmail;
    private int proMemPhoneMid;
    private int proMemPhoneBack;
}
