package com.example.menbosa.dto.protector.communicate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class FileDto {
    private Long fileNum;
    private String fileUser;
    private String fileServer;
    private String fileExt;
    private Long boardCommuNum;
}
