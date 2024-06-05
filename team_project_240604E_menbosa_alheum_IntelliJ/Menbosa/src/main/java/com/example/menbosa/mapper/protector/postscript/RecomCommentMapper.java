package com.example.menbosa.mapper.protector.postscript;

import com.example.menbosa.dto.protector.page.Criteria;
import com.example.menbosa.dto.protector.postscript.RecomCommentListDTO;
import com.example.menbosa.dto.protector.postscript.RecomCommentWriteDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RecomCommentMapper {
    void insertRecomComment(RecomCommentWriteDTO commentWriteDTO);

    List<RecomCommentListDTO> selectRecomCommentList(Long boardRecomNum);

    void deleteRecomComment(Long boardRecomNum);

    List<RecomCommentListDTO> selectRecomSlice(@Param("criteria") Criteria criteria, @Param("boardRecomNum") Long boardRecomNum);
}
