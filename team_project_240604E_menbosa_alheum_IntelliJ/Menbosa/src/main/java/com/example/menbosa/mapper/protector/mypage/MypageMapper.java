package com.example.menbosa.mapper.protector.mypage;

import com.example.menbosa.dto.protector.mypage.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MypageMapper {
    List<ProMypageResultDTO> selectMyResult(long proMemNum);

    List<ProMypageSenInfoDTO> selectMySenInfo(long proMemNum);

    List<ProMypageBoardDTO> selectMyBoard(long proMemNum);

    ProMypageInfoDTO selectMyInfo(long proMemNum);

    ProMypageSenDetailsDTO selectMySenDetails(long senMemNum);

    void updateMyModify(ProMypageModifyDTO proMypageModifyDTO);

    void updateSenAddNew(ProMypageSenConnecDTO proMypageSenConnecDTO);

    void updateSenAddClear(long senMemNum);

    Optional<Long> selectFindSenMem(ProMypageFindSenDTO proMypageFindSenDTO);

    Optional<String> selectCheckPassword(long proMemNum);

    String selectFindEmail(String proMemEmail);

    void updateOnlyEmail(String proMemEmail, String proMemNumString);

    void deleteProMem(long proMemNum);

//    sms
    Long selectProExi(String name, String phoneNum);
    Long selectSenExi(String name, String phoneNum);

    void updateProCerti(String smsMessagePost, String proMemNum);
    void updateSenCerti(String smsMessagePost, String senMemNum);
}
