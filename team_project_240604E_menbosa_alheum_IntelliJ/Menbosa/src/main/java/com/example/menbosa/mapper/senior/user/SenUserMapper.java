package com.example.menbosa.mapper.senior.user;

import com.example.menbosa.dto.senior.user.SenUserDTO;
import com.example.menbosa.dto.senior.user.SenUserSessionDTO;
import com.example.menbosa.dto.senior.user.SenUserUpdateDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface SenUserMapper {
    void insertSenUser(SenUserDTO senUserDTO);

//    selectSenLogin
    Optional<SenUserSessionDTO> selectSenLogin(String senMemPhone, String senMemPassword);

    void deleteSenUser(Long senMemNum);

    void updateSenInfo(SenUserUpdateDTO updateDTO);

    String selectCheckSenPassword(Long senMemNum);
}
