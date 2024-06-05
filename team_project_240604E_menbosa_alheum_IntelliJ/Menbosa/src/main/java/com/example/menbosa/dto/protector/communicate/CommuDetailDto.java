package com.example.menbosa.dto.protector.communicate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter @ToString
@NoArgsConstructor
public class CommuDetailDto {

    private Long boardCommuNum;
    private String boardCommuTitle;
    private String boardCommuContents;
    private LocalDateTime boardCommuDate;
    private String proMemName;
    private Long proMemNum;
    private String fileUser;
}
