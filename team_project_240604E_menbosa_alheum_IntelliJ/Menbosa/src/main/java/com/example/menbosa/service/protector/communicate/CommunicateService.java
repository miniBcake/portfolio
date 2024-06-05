package com.example.menbosa.service.protector.communicate;

import com.example.menbosa.dto.protector.communicate.*;
import com.example.menbosa.dto.protector.page.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;

@Service
public interface CommunicateService {
    //    공지 목록 조회
    List<MainListDto> findAll();

    //    공지 상세글 조회
    MainViewDto findByNum(Long announceNum);

    //    공지 페이징 처리
    List<MainListDto> findAllPageAnno(Criteria criteria);
    int findTotalAnno();

    //    소통 게시글 목록
    List<CommuListDto> selectCommuList();

    //    소통 글쓰기
    void registerCommu(CommuWriteDto commuWriteDto);

    // 글쓰기 , 파일첨부
    void registerCommuWithFile(CommuWriteDto commuWriteDto, List<MultipartFile> files) throws IOException;

    // 파일 저장
    FileDto saveFile(MultipartFile files) throws IOException;

    // 소통 상세글 조회
    CommuDetailDto findCommuDetail(Long boardCommuNum);


    // 소통 글수정하기
    void modifyCommu(CommuUpdateDto commuUpdateDto, List<MultipartFile> files) throws IOException;

    // 소통 글 삭제하기
    void removeCommu(Long boardCommuNum);


    // 소통 페이징 처리
    List<CommuListDto> findAllPage(Criteria criteria);

    int findTotal();

    // 소통 검색
//    List<CommuListDto> findSearch(String search);

}
