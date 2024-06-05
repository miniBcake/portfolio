package com.example.menbosa.dto.protector.communicate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@Component
public class CommentWriteDto {
    private Long commentCommuNum;
    private LocalDateTime commentCommuDate;
    private String commentCommuContents;
    private Long boardCommuNum;
    private Long proMemNum;
}
