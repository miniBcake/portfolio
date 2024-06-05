package com.example.menbosa.service.protector.communicate;

import com.example.menbosa.dto.protector.communicate.CommentListDto;
import com.example.menbosa.dto.protector.communicate.CommentWriteDto;
import com.example.menbosa.dto.protector.page.Criteria;
import com.example.menbosa.dto.protector.page.Slice;
import com.example.menbosa.mapper.protector.communicate.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentMapper commentMapper;

    @Override
    public void registerComment(CommentWriteDto commentWriteDto) {
        System.out.println("====" + commentWriteDto);
        commentMapper.insertComment(commentWriteDto);

    }

    @Override
    public List<CommentListDto> findCommentList(Long boardCommuNum) {
        return commentMapper.selectCommentList(boardCommuNum);
    }

    //페이지네이션 댓글 목록
    @Override
    public Slice<CommentListDto> findSlice(Criteria criteria, Long boardCommuNum) {
        List<CommentListDto> commentList = commentMapper.selectSlice(criteria, boardCommuNum);
        boolean hasNext = commentList.size() > criteria.getAmount();
        if(hasNext){
            commentList.remove(criteria.getAmount());
        }

        return new Slice<CommentListDto>(hasNext, commentList);
    }

    @Override
    public void removeComment(Long commentCommuNum) {
        commentMapper.deleteComment(commentCommuNum);
    }
}
