package com.example.menbosa.service.protector.postscript;

import com.example.menbosa.dto.protector.communicate.CommentListDto;
import com.example.menbosa.dto.protector.page.Criteria;
import com.example.menbosa.dto.protector.page.Slice;
import com.example.menbosa.dto.protector.postscript.RecomCommentListDTO;
import com.example.menbosa.dto.protector.postscript.RecomCommentWriteDTO;
import com.example.menbosa.mapper.protector.postscript.RecomCommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RecomCommentServiceImpl implements RecomCommentService {

    private final RecomCommentMapper recomCommentMapper;


    @Override
    public void registerRecomComment(RecomCommentWriteDTO recomCommentWriteDTO) {
        recomCommentMapper.insertRecomComment(recomCommentWriteDTO);
    }

    @Override
    public List<RecomCommentListDTO> findRecomCommentList(Long boardRecomNum) {
        return recomCommentMapper.selectRecomCommentList(boardRecomNum);
    }

    @Override
    public Slice<RecomCommentListDTO> findRecomSlice(Criteria criteria, Long boardRecomNum) {
        List<RecomCommentListDTO> recomCommentList = recomCommentMapper.selectRecomSlice(criteria, boardRecomNum);
        boolean hasNext = recomCommentList.size() > criteria.getAmount();
        if(hasNext){
            recomCommentList.remove(criteria.getAmount());
        }

        return new Slice<RecomCommentListDTO>(hasNext, recomCommentList);
    }

    @Override
    public void removeRecomComment(Long boardRecomNum) {
        recomCommentMapper.deleteRecomComment(boardRecomNum);
    }
}
