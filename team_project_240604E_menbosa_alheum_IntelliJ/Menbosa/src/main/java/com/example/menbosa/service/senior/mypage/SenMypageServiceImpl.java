package com.example.menbosa.service.senior.mypage;

import com.example.menbosa.dto.senior.mypage.SenMyInfoDTO;
import com.example.menbosa.dto.senior.mypage.SenMyProInfoDTO;
import com.example.menbosa.dto.senior.mypage.SenMyTestInfoDTO;
import com.example.menbosa.mapper.senior.mypage.SenMypageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SenMypageServiceImpl implements SenMypageService{

    private final SenMypageMapper senMypageMapper;

    @Override
    public List<SenMyInfoDTO> selectSenMyInfo(long senMemNum) {
        return senMypageMapper.selectSenMyInfo(senMemNum);
    }

    @Override
    public List<SenMyProInfoDTO> selectSenMyProInfo(long senMemNum) {
        return senMypageMapper.selectSenMyProInfo(senMemNum);
    }

    @Override
    public List<SenMyTestInfoDTO> selectSenMyTestInfo(long senMemNum) {
        return senMypageMapper.selectSenMyTestInfo(senMemNum);
    }


}
