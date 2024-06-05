package com.example.menbosa.dto.protector.user;

import lombok.*;

@Setter @Getter @ToString
@NoArgsConstructor
public class UserDTO {
    private Long proMemNum;
    private String proMemName;
    private int proMemPhoneMid;
    private int proMemPhoneBack;
    private String proMemPhone;
    private int proMemCertification;
    private String proMemPassword;
    private String proMemPasswordVerify;
    private String proMemEmail;
}
