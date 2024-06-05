package com.example.menbosa.service.senior.user;

import com.example.menbosa.dto.senior.user.SenUserDTO;
import com.example.menbosa.dto.senior.user.SenUserSessionDTO;
import com.example.menbosa.dto.senior.user.SenUserUpdateDTO;
import com.example.menbosa.mapper.senior.user.SenUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SenUserServiceImpl implements  SenUserService{

    private final SenUserMapper senUserMapper;

    @Override
    public void insertSenUser(SenUserDTO senUserDTO) {
        System.out.println(senUserDTO);
        senUserMapper.insertSenUser(senUserDTO);
    }

    @Override
    public SenUserSessionDTO loginSenUser(String senMemPhone, String senMemPassword) {
        return senUserMapper.selectSenLogin(senMemPhone, senMemPassword).orElseThrow(() -> new IllegalStateException("로그인 실패"));
    }

    @Override
    public void deleteSenUser(long senMemNum) {
        senUserMapper.deleteSenUser(senMemNum);
    }

    @Override
    public void updateSenInfo(SenUserUpdateDTO senUserUpdateDTO) {
        senUserMapper.updateSenInfo(senUserUpdateDTO);
    }

    @Override
    public String selectCheckSenPassword(long senMemNum) {
        return senUserMapper.selectCheckSenPassword(senMemNum);
    }

}
