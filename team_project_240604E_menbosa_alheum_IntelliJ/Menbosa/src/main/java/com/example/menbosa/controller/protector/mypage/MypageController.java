package com.example.menbosa.controller.protector.mypage;

import com.example.menbosa.dto.protector.communicate.CommuDetailDto;
import com.example.menbosa.dto.protector.communicate.CommuUpdateDto;
import com.example.menbosa.dto.protector.mypage.*;
import com.example.menbosa.dto.senior.mypage.SenMyTestInfoDTO;
import com.example.menbosa.service.protector.communicate.CommunicateService;
import com.example.menbosa.service.protector.mypage.MypageService;
import com.example.menbosa.service.protector.postscript.PostService;
import com.example.menbosa.service.senior.mypage.SenMypageService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/alheum/mypage")
@RequiredArgsConstructor
public class MypageController {

    private final MypageService mypageService;
    private final ProMypageSenConnecDTO proMypageSenConnecDTO;
    private final SenMypageService senMypageService;

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session){
        session.invalidate();
        return new RedirectView("/alheum/user/login");
    }

    @GetMapping("/leave")
    public RedirectView leave(@SessionAttribute("proMemNum")Long proMemNum, HttpSession session){
        mypageService.deleteProMem(proMemNum);
        session.invalidate();
        return new RedirectView("/alheum/user/login");
    }

    //   메인 마이페이지 화면
    @GetMapping
    public String mypage(Model model, @SessionAttribute("proMemNum")Long proMemNum) {
        List<ProMypageResultDTO> MyResultList = mypageService.selectMyResult(proMemNum);
        List<ProMypageSenInfoDTO> MySenInfoList = mypageService.selectMySenInfo(proMemNum);
        List<ProMypageBoardDTO> MyBoardList = mypageService.selectMyBoard(proMemNum);

        model.addAttribute("MyResultList", MyResultList);
        model.addAttribute("MySenInfoList", MySenInfoList);
        model.addAttribute("MyBoardList", MyBoardList);
        return "/protector/protectorMypage-main";
    }

    //  마이페이지 시니어 상세보기
    @GetMapping("/seninfo")
    public String mypageSenInfo(Model model, @RequestParam("senMemNum")Long senMemNum) {
        ProMypageSenDetailsDTO MySenDetails = mypageService.selectMySenDetails(senMemNum);
        List<SenMyTestInfoDTO> senMyTestInfo = senMypageService.selectSenMyTestInfo(senMemNum);
        model.addAttribute("MySenDetails", MySenDetails);
        model.addAttribute("MySenInfoList", senMyTestInfo);
        return "/protector/protectorMypage-seniorDetails";
    }

    // 시니어 연결 해제
    @GetMapping("/deleteSenMem")
    public RedirectView mypageSenConnectDelete(@RequestParam("senMemNum")Long senMemNum){
        mypageService.updateSenAddClear(senMemNum);
        return new RedirectView("/alheum/mypage");
    }

    //  검증페이지
    @PostMapping("/check")
    public String mypageCheck(Model model, String password ,@SessionAttribute("proMemNum")Long proMemNum ) {
        String passwordDb = mypageService.selectCheckPassword(proMemNum);
        return passwordDb.equals(password)? "redirect:modify" : "redirect:check";
    }

    @GetMapping("/check")
    public String getMypageCheck(Model model) {
        return "/basic/passwordVerify-mypage";
    }

    //    개인정보 수정 페이지
    @GetMapping("/modify")
    public String mypageModify(Model model ,@SessionAttribute("proMemNum")Long proMemNum) {
        ProMypageInfoDTO MyInfo = mypageService.selectMyInfo(proMemNum);
        model.addAttribute("MyInfo", MyInfo);
        return "/protector/protectorMypage-modify";
    }

    @PostMapping("/modify")
    public String mypageModify( @SessionAttribute("proMemNum") Long proMemNum, ProMypageModifyDTO proMypageModifyDTO) {
        proMypageModifyDTO.setProMemNum(proMemNum);

        try {
            if (!proMypageModifyDTO.getProMemEmail().equals(mypageService.selectMyInfo(proMemNum).getProMemEmail())) {
                //이메일이 수정되었으니 검증 진행
                if (mypageService.selectFindEmail(proMypageModifyDTO.getProMemEmail())) {
                    //이미 db에 있는 이메일이다
                    throw new Exception("변경할 수 없는 이메일입니다");
                }
            }

            if (proMypageModifyDTO.getProMemPassword().isEmpty()) {
                // 비밀번호가 비어있다면 이메일만 저장
                mypageService.updateOnlyEmail(proMypageModifyDTO.getProMemEmail(), proMemNum);
            } else {
                //이메일이 수정되지 않음 또는 수정 가능한 이메일
                mypageService.updateMyModify(proMypageModifyDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/alheum/mypage/modify";
    }

    //    어르신 등록페이지
    @GetMapping("/seniorAdd")
    public String mypageSeniorAdd(Model model){
        return "/protector/protectorMypage-seniorAdd";
    }

    @PostMapping("/seniorAdd")
    public String mypageSeniorAdd(@SessionAttribute("proMemNum") Long proMemNum, ProMypageFindSenDTO proMypageFindSenDTO){
        try {
            proMypageSenConnecDTO.setSenMemNum(mypageService.selectFindSenMem(proMypageFindSenDTO));
            proMypageSenConnecDTO.setProMemNum(proMemNum);

            mypageService.updateSenAddNew(proMypageSenConnecDTO);
            System.out.println("연결완료");
        }catch (IllegalStateException e){
            return "redirect:/alheum/mypage/seniorAdd";
        }
        return "redirect:/alheum/mypage";
    }

//-------------------------------------------------------------------------------------------
//소통 글 삭제페이지
    private final CommunicateService communicateService;
    private final PostService postService;

    @GetMapping("/commuRemove")
    public RedirectView commuRemove(Long boardCommuNum){
        communicateService.removeCommu(boardCommuNum);
        return new RedirectView("/alheum/mypage");
    }

    @GetMapping("/recomRemove")
    public RedirectView recomRemove(Long boardRecomNum){
        postService.removePost(boardRecomNum);
        return new RedirectView("/alheum/mypage");
    }
}
