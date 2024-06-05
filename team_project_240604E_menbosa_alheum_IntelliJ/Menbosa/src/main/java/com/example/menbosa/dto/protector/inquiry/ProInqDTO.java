package com.example.menbosa.dto.protector.inquiry;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class ProInqDTO {
    private long boardInquNum;
    private String boardInquTitle;
    private LocalDateTime boardInquDate;
//    private long proMemNum;
}
