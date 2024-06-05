package com.example.menbosa.dto.protector.inquiry;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
public class ProInqDetailsDTO {
    private long proMemNum;
    private String boardInquTitle;
    private String boardInquContents;
    private LocalDateTime boardInquDate;
}
