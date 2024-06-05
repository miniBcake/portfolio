package com.example.menbosa.dto.senior.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Component
public class SenUserDTO {
    private long senMemNum;
    private String senMemName;
    private LocalDate senMemBirth;
    private int senMemPhoneMid;
    private int senMemPhoneBack;
    private String senMemPhone;
    private long senMemCertification;
    private String senMemPassword;
    private String senMemPasswordVerify;
}
