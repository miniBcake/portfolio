package com.example.menbosa.dto.protector.postscript;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ImgFileDTO {
    private Long imgFileNum;
    private String imgFileUser;
    private String imgFileExt;
    private String imgFileServer;
    private Long boardRecomNum;
}
