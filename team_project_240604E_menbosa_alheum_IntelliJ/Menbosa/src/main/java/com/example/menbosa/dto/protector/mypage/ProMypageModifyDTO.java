package com.example.menbosa.dto.protector.mypage;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class ProMypageModifyDTO {
    private long proMemNum;
    private String proMemEmail;
    private String proMemPassword;
    private String proMemPasswordVerify;
}
