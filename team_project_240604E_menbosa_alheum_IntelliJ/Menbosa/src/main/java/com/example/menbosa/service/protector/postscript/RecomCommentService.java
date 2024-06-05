package com.example.menbosa.service.protector.postscript;

import com.example.menbosa.dto.protector.communicate.CommentListDto;
import com.example.menbosa.dto.protector.communicate.CommentWriteDto;
import com.example.menbosa.dto.protector.page.Criteria;
import com.example.menbosa.dto.protector.page.Slice;
import com.example.menbosa.dto.protector.postscript.RecomCommentListDTO;
import com.example.menbosa.dto.protector.postscript.RecomCommentWriteDTO;

import java.util.List;

public interface RecomCommentService {
    void registerRecomComment(RecomCommentWriteDTO recomCommentWriteDTO);

    List<RecomCommentListDTO> findRecomCommentList(Long boardRecomNum);

    Slice<RecomCommentListDTO> findRecomSlice(Criteria criteria, Long boardRecomNum);

    void removeRecomComment(Long boardRecomNum);
}
