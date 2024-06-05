package com.example.menbosa.dto.protector.communicate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor
public class MainListDto {
    private Long announceNum;
    private String announceTitle;
    private LocalDate announceDate;
}