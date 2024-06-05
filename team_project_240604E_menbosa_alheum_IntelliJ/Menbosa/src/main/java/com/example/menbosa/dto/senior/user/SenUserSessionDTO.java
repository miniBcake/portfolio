package com.example.menbosa.dto.senior.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SenUserSessionDTO {
    private Long senMemNum;
    private int senMemPhone;
    private String senMemName;
}
