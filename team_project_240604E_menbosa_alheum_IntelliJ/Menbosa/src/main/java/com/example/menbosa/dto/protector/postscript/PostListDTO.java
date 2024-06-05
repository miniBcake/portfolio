package com.example.menbosa.dto.protector.postscript;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PostListDTO {
    private Long boardRecomNum;
    private String boardRecomTitle;
    private String proMemPhone;
    private String proMemName;
    private LocalDateTime boardRecomDate;
    private Long IMG_FILE_NUM;
    private String IMG_FILE_USER;
    private String IMG_FILE_SERVER;
    private String IMG_FILE_EXT;
}
