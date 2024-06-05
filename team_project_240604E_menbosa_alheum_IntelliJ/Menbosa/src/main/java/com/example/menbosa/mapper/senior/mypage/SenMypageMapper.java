package com.example.menbosa.mapper.senior.mypage;

import com.example.menbosa.dto.senior.mypage.SenMyInfoDTO;
import com.example.menbosa.dto.senior.mypage.SenMyProInfoDTO;
import com.example.menbosa.dto.senior.mypage.SenMyTestInfoDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SenMypageMapper {
    List<SenMyInfoDTO> selectSenMyInfo(Long senMemNum);
    List<SenMyProInfoDTO> selectSenMyProInfo(Long senMemNum);
    List<SenMyTestInfoDTO> selectSenMyTestInfo(Long senMemNum);
}
