package com.example.menbosa.service.protector.mypage;

import com.example.menbosa.dto.protector.mypage.ProMypageFindSenDTO;
import com.example.menbosa.dto.protector.mypage.ProMypageModifyDTO;
import com.example.menbosa.dto.protector.mypage.ProMypageSenConnecDTO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MypageServiceimplTest {
    private static final Logger log = LoggerFactory.getLogger(MypageServiceimplTest.class);
    @Autowired
    private MypageService mypageService;
    @Autowired
    private ProMypageModifyDTO proMypageModifyDTO;
    @Autowired
    private ProMypageSenConnecDTO proMypageSenConnecDTO;
    @Autowired
    private ProMypageFindSenDTO proMypageFindSenDTO;

    @Test
    void selectSMS(){
        System.out.println(mypageService.selectSenExi("한지윤","01073967653"));
    }

//    @Test
//    void selectMyResult() {
//        System.out.println(mypageService.selectMyResult(600));
//    }
//
//    @Test
//    void selectMySenInfo() {
//        System.out.println(mypageService.selectMySenInfo(600));
//    }
//
//    @Test
//    void selectMyBoard() {
//        System.out.println(mypageService.selectMyBoard(600));
//    }
//
//    @Test
//    void selectMyInfo() {
//        System.out.println(mypageService.selectMyInfo(600));
//    }
//
//    @Test
//    void updateMyinfo() {
//        proMypageModifyDTO = new ProMypageModifyDTO();
//        proMypageModifyDTO.setProMemNum(600);
//        proMypageModifyDTO.setProMemEmail("test@email.com");
//        proMypageModifyDTO.setProMemPassword("123456");
//        proMypageModifyDTO.setProMemPasswordVerify("123456");
//
//        mypageService.updateMyModify(proMypageModifyDTO);
//    }
//
//    @Test
//    void updateSenAddNew(){
//        proMypageSenConnecDTO = new ProMypageSenConnecDTO();
//        proMypageSenConnecDTO.setProMemNum(600);
//        proMypageSenConnecDTO.setSenMemNum(600);
//
//        mypageService.updateSenAddNew(proMypageSenConnecDTO);
//    }
//
//    @Test
//    void updateSenAddClear(){
//        mypageService.updateSenAddClear(600);
//    }
//
//    @Test
//    void findSenMem(){
//        proMypageFindSenDTO.setSenMemNum(600);
//        proMypageFindSenDTO.setSenMemPhoneMid(1234);
//        proMypageFindSenDTO.setSenMemPhoneBack(4567);
//        try {
//            mypageService.selectFindSenMem(proMypageFindSenDTO);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    void checkEmail(){
//        System.out.println(mypageService.selectFindEmail("test3@email.com"));
//    }
//
//    @Test
//    void updateOnlyEmail(){
//        mypageService.updateOnlyEmail("test3@email.com", 600);
//    }
//
//    @Test
//    void deleteUserpro(){
//        mypageService.deleteProMem(603);
//    }
}