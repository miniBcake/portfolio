package com.example.menbosa.mapper.protector.communicate;

import com.example.menbosa.dto.protector.communicate.CommentListDto;
import com.example.menbosa.dto.protector.communicate.CommentWriteDto;
import com.example.menbosa.dto.protector.page.Criteria;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    void insertComment(CommentWriteDto commentWriteDto);

    List<CommentListDto> selectCommentList(Long boardCommuNum);

    void deleteComment(Long commentCommuNum);

    List<CommentListDto> selectSlice(@Param("criteria")Criteria criteria, @Param("boardCommuNum") Long boardCommuNum);
}
