package com.example.menbosa.service.protector.communicate;

import com.example.menbosa.dto.protector.communicate.CommentListDto;
import com.example.menbosa.dto.protector.communicate.CommentWriteDto;
import com.example.menbosa.dto.protector.page.Criteria;
import com.example.menbosa.dto.protector.page.Slice;

import java.util.List;

public interface CommentService {
    void registerComment(CommentWriteDto commentWriteDto);

    List<CommentListDto> findCommentList(Long boardCommuNum);

    Slice<CommentListDto> findSlice(Criteria criteria, Long boardCommuNum);

    void removeComment(Long commentCommuNum);
}
