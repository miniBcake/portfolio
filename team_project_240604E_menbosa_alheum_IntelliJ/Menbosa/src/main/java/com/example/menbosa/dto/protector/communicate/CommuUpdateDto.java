package com.example.menbosa.dto.protector.communicate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@NoArgsConstructor
public class CommuUpdateDto {
    private Long boardCommuNum;
    private String boardCommuTitle;
    private String boardCommuContents;
}
