package com.example.menbosa.service.senior.mypage;

import com.example.menbosa.dto.senior.mypage.SenMyInfoDTO;
import com.example.menbosa.dto.senior.mypage.SenMyProInfoDTO;
import com.example.menbosa.dto.senior.mypage.SenMyTestInfoDTO;

import java.util.List;

public interface SenMypageService {
    List<SenMyInfoDTO> selectSenMyInfo(long senMemNum);
    List<SenMyProInfoDTO> selectSenMyProInfo(long senMemNum);
    List<SenMyTestInfoDTO> selectSenMyTestInfo(long senMemNum);

}
