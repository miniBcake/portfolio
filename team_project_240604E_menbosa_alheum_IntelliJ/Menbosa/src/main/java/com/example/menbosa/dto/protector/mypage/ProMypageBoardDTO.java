package com.example.menbosa.dto.protector.mypage;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class ProMypageBoardDTO {
    private String boardTitle;
    private LocalDateTime boardDate;
    private int division;
    private Long boardNum;
}
