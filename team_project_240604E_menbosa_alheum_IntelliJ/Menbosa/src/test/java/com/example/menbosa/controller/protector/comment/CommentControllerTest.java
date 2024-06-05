package com.example.menbosa.controller.protector.comment;

import com.example.menbosa.dto.protector.communicate.CommentWriteDto;
import com.example.menbosa.mapper.protector.communicate.CommentMapper;
import com.example.menbosa.mapper.protector.communicate.CommunicateMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class CommentControllerTest {
    @Autowired
    private CommentMapper commentMapper;

    @Test
    void insertComment(){
        CommentWriteDto commentWriteDto = new CommentWriteDto();

        commentWriteDto.setProMemNum(600L);
        commentWriteDto.setCommentCommuNum(600L);
        commentWriteDto.setBoardCommuNum(600L);
        commentWriteDto.setCommentCommuContents("content2");
        commentWriteDto.setCommentCommuDate(LocalDateTime.now());

       commentMapper.insertComment(commentWriteDto);
        System.out.println(commentMapper);
        System.out.println(commentWriteDto);
    }
}
