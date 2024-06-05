package com.example.menbosa.service.senior.user;

import com.example.menbosa.dto.senior.user.SenUserDTO;
import com.example.menbosa.dto.senior.user.SenUserSessionDTO;
import com.example.menbosa.dto.senior.user.SenUserUpdateDTO;

import java.util.Optional;

public interface SenUserService {
    void insertSenUser(SenUserDTO senUserDTO);
    SenUserSessionDTO loginSenUser(String senMemPhone, String senMemPassword);
    void deleteSenUser(long senMemNum);
    void updateSenInfo(SenUserUpdateDTO senUserUpdateDTO);
    String selectCheckSenPassword(long senMemNum);
}
