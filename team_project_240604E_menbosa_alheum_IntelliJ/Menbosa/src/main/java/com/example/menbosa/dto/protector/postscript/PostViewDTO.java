package com.example.menbosa.dto.protector.postscript;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostViewDTO {
    private Long boardRecomNum;
    private String boardRecomTitle;
    private String boardRecomContents;
    private LocalDateTime boardRecomDate;
    private String proMemPhone;
    private Long proMemNum;
    private String proMemName;
}
