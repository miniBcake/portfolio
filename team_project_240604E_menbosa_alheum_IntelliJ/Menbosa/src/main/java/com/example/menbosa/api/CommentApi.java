package com.example.menbosa.api;

import com.example.menbosa.dto.protector.communicate.CommentListDto;
import com.example.menbosa.dto.protector.communicate.CommentWriteDto;

import com.example.menbosa.dto.protector.page.Criteria;
import com.example.menbosa.dto.protector.page.Slice;
import com.example.menbosa.service.protector.communicate.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentApi {
    private final CommentService commentService;

    // 댓글작성
    @PostMapping("/v1/communicate/{boardCommuNum}/comment")
    public void commentWrite(@RequestBody CommentWriteDto commentWriteDto,
                             @PathVariable("boardCommuNum") Long boardCommuNum,
                             @SessionAttribute("proMemNum") Long proMemNum) {
        commentWriteDto.setBoardCommuNum(boardCommuNum);
        commentWriteDto.setProMemNum(proMemNum);
        System.out.println("boardCommuNum = " + boardCommuNum + ", proMemNum = " + proMemNum);
        System.out.println(commentWriteDto.getCommentCommuContents());

        commentService.registerComment(commentWriteDto);

        System.out.println("된다고해줘제발");
    }

    //댓글목록
    @GetMapping("/v1/communicate/{boardCommuNum}/comments")
    public List<CommentListDto> commentList(@PathVariable("boardCommuNum") Long boardCommuNum){
        return commentService.findCommentList(boardCommuNum);
    }

    //페이지네이션
    @GetMapping("/v2/communicate/{boardCommuNum}/comments")
    public Slice<CommentListDto> commentSlice(@PathVariable("boardCommuNum") Long boardCommuNum, int page){
        Slice<CommentListDto> slice = commentService.findSlice(new Criteria(page, 5), boardCommuNum);
        return slice;
    }

    //댓글삭제
    @DeleteMapping("/v1/comments/{commentCommuNum}")
    public void deleteComment(@PathVariable("commentCommuNum") Long commentCommuNum){
        commentService.removeComment(commentCommuNum);
    }

}
