package com.example.menbosa.dto.protector.communicate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
public class MainViewDto {
    private Long announceNum;
    private String announceTitle;
    private String announceContents;
    private LocalDateTime announceDate;
}