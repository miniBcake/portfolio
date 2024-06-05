package com.example.menbosa.dto.protector.mypage;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class ProMypageSenDetailsDTO {
    private String senMemName;
    private LocalDateTime senMemBirth;
    private int senMemPhoneMid;
    private int senMemPhoneBack;
}
