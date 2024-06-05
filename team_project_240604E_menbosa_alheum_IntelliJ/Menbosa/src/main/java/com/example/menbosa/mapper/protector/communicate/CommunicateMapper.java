package com.example.menbosa.mapper.protector.communicate;

import com.example.menbosa.dto.protector.communicate.*;
import com.example.menbosa.dto.protector.page.Criteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CommunicateMapper {


//    공지 목록 조회
    List<MainListDto> selectMain();

//    공지 상세글 조회
    MainViewDto selectByNum(Long announceNum);


//    공지 페이징 처리
    List<MainListDto> selectAllPageAnno(Criteria criteria);

    int selectTotalAnno();


//    소통 게시글 목록
    List<CommuListDto> selectCommuList();

//    소통 글쓰기
    void insertCommu(CommuWriteDto commuWriteDto);

//    소통 상세글 조회하기
    Optional<CommuDetailDto> selectCommuDetail(Long boardCommuNum);
    
//    소통 글수정하기
    void updateCommu(CommuUpdateDto commuUpdateDto);

//    소통 글 삭제하기
    void deleteCommu(Long boardCommuNum);

//    소통 페이징 처리
    List<CommuListDto> selectAllPage(Criteria criteria);

    int selectTotal();

//    소통 검색
//    List<CommuListDto> selectSearch(String search);

}
