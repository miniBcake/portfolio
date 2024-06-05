package com.example.menbosa.service.protector.mypage;

import com.example.menbosa.dto.protector.mypage.*;

import java.util.List;

public interface MypageService {
    List<ProMypageResultDTO> selectMyResult(long proMemNum);

    List<ProMypageSenInfoDTO> selectMySenInfo(long proMemNum);

    List<ProMypageBoardDTO> selectMyBoard(long proMemNum);

    ProMypageInfoDTO selectMyInfo(long proMemNum);

    ProMypageSenDetailsDTO selectMySenDetails(long senMemNum);

    void updateMyModify(ProMypageModifyDTO proMypageModifyDTO);

    void updateSenAddNew(ProMypageSenConnecDTO proMypageSenConnecDTO);

    void updateSenAddClear(long senMemNum);

    Long selectFindSenMem(ProMypageFindSenDTO proMypageFindSenDTO);

    String selectCheckPassword(long proMemNum);

    boolean selectFindEmail(String proMemEmail);

    void updateOnlyEmail(String proMemEmail, long proMemNum);

    void deleteProMem(long proMemNum);
    //    sms
    Long selectProExi(String name, String phoneNum);
    Long selectSenExi(String name, String phoneNum);

    void updateProCerti(String smsMessagePost, String proMemNum);
    void updateSenCerti(String smsMessagePost, String senMemNum);
}
