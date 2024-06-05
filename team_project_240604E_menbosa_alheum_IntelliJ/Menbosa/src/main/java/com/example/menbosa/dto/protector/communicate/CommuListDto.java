package com.example.menbosa.dto.protector.communicate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter @Setter @ToString
@NoArgsConstructor
public class CommuListDto {
    private Long boardCommuNum;
    private String boardCommuTitle;
    private String boardCommuContents;
    private LocalDate boardCommuDate;
    private String proMemName;
}
