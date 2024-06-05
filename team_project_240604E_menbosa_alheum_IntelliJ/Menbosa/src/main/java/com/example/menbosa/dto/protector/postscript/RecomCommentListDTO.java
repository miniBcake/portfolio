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
public class RecomCommentListDTO {
    private Long commentRecomNum;
    private LocalDateTime commentRecomDate;
    private String commentRecomContents;
    private Long boardRecomNum;
    private String proMemName;
    private Long proMemNum;
}
