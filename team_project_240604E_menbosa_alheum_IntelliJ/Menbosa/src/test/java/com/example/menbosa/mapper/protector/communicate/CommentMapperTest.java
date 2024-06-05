package com.example.menbosa.mapper.protector.communicate;

import com.example.menbosa.dto.protector.communicate.CommentListDto;
import com.example.menbosa.dto.protector.communicate.CommentWriteDto;
//import com.example.menbosa.dto.protector.communicate.CommentWriteDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentMapperTest {

    @Autowired
    private CommentWriteDto commentWriteDto;

    @Autowired
    private CommentListDto commentListDto;

    @Autowired
    CommentMapper commentMapper;

    @Test
    void insertComment() {
        commentWriteDto = new CommentWriteDto();
        commentWriteDto.setProMemNum(601L);
        commentWriteDto.setCommentCommuContents("안녕못해");
        commentWriteDto.setBoardCommuNum(35L);
//        commentWriteDto.setCommentCommuDate(TO_DATE('2024-01-01', 'YYYY-MM-DD'));
        commentWriteDto.setCommentCommuNum(408L);
        commentMapper.insertComment(commentWriteDto);
    }

    @Test
    void selectCommentList() {
        commentListDto = new CommentListDto();
        System.out.println(commentListDto.getCommentCommuNum());
        System.out.println(commentListDto.getBoardCommuNum());
        System.out.println(commentListDto.getProMemName());
        System.out.println(commentListDto.getCommentCommuContents());

    }
}