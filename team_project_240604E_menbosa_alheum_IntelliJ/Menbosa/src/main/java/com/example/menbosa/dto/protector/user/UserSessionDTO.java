package com.example.menbosa.dto.protector.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSessionDTO {
    private Long proMemNum;
    private int proMemPhone;
    private String proMemName;
}
