package com.example.menbosa.dto.senior.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor

public class SenUserUpdateDTO {
    private Long senMemNum;
    private LocalDate senMemBirth;
    private String senMemPassword;
    private String senMemPasswordVerify;
}
