package com.example.menbosa.controller.senior.user;

import com.example.menbosa.dto.protector.user.UserSessionDTO;
import com.example.menbosa.dto.senior.mypage.SenMyInfoDTO;
import com.example.menbosa.dto.senior.mypage.SenMyProInfoDTO;
import com.example.menbosa.dto.senior.mypage.SenMyTestInfoDTO;
import com.example.menbosa.dto.senior.user.SenUserDTO;
import com.example.menbosa.dto.senior.user.SenUserSessionDTO;
import com.example.menbosa.dto.senior.user.SenUserUpdateDTO;
import com.example.menbosa.service.senior.mypage.SenMypageService;
import com.example.menbosa.service.senior.user.SenUserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/alheum/senUser")
@RequiredArgsConstructor
public class SenUserController {

    private final SenMypageService senMypageService;
    private final SenUserService senUserService;

    @GetMapping("/login")
    public String login(){

        return "senior/seniorLogin";
    }

    @PostMapping("/login")
    public String login(String senMemPhone, String senMemPassword, HttpSession session){
//        session.invalidate();
        try {
            SenUserSessionDTO sessionDTO = senUserService.loginSenUser(senMemPhone, senMemPassword);
            session.setAttribute("senMemNum", sessionDTO.getSenMemNum());
            session.setAttribute("senMemName", sessionDTO.getSenMemName());
            session.setAttribute("testListNum", 0L);
        } catch (IllegalStateException e) {
            return "redirect:/alheum/senUser/login";
        }
        return "redirect:/alheum/senUser/main";
    }

    @GetMapping("/join")
    public String join() {
        return "senior/seniorJoin";
    }

    @PostMapping("/join")
    public String join(SenUserDTO senUserDTO){
        senUserService.insertSenUser(senUserDTO);
        return "redirect:/alheum/senUser/login";
    }

    @GetMapping("/findPassword")
    public String findPassword(){

        return "senior/seniorPassword-find";
    }

    @GetMapping("/main")
    public String main(){

        return "senior/seniorMain";
    }

    @GetMapping("/mypage")
    public String mypage(Model model, @SessionAttribute("senMemNum") Long senMemNum){
        List<SenMyInfoDTO> senMyInfo = senMypageService.selectSenMyInfo(senMemNum);
        List<SenMyProInfoDTO> senMyProInfo = senMypageService.selectSenMyProInfo(senMemNum);
        List<SenMyTestInfoDTO> senMyTestInfo = senMypageService.selectSenMyTestInfo(senMemNum);

        model.addAttribute("senMyInfo", senMyInfo);
        model.addAttribute("senMyProInfo", senMyProInfo);
        model.addAttribute("senMyTestInfo", senMyTestInfo);

        return "senior/seniorMypage";
    }

    @GetMapping("/logout")
    public RedirectView logout(HttpSession session){
        session.invalidate();
        return new RedirectView("/alheum/senUser/login");
    }

    @GetMapping("/proLogin")
    public RedirectView proLogin(HttpSession session){
        session.invalidate();
        return new RedirectView("/alheum/user/login");
    }

    @GetMapping("/delete")
    public RedirectView delete(@SessionAttribute("senMemNum") Long senMemNum, HttpSession session){
        senUserService.deleteSenUser(senMemNum);
        System.out.println(senMemNum + "============");
        session.invalidate();
        return new RedirectView("/alheum");
    }

//    @RequestParam("senMemBirth") LocalDate senMemBirth,
//                              @RequestParam("senMemPassword") String senMemPassword,
//                              @RequestParam("senMemPasswordVerify") String senMemPasswordVerify

    @GetMapping("/update")
    public String update(){

        return "senior/seniorMypage";
    }

    @PostMapping("/update")
    public String update(
            @ModelAttribute SenUserUpdateDTO senUserUpdateDTO,
            @SessionAttribute("senMemNum") Long senMemNum,
            @RequestParam("senMemBirth") String birth
    ) {
        senUserUpdateDTO.setSenMemNum(senMemNum);
        LocalDate parsedBirth = LocalDate.parse(birth); // 문자열을 LocalDate로 파싱
        senUserUpdateDTO.setSenMemBirth(parsedBirth);

//        // 비밀번호 일치 확인 (선택사항)
//        if (!senUserUpdateDTO.getSenMemPassword().equals(senUserUpdateDTO.getSenMemPasswordVerify())) {
//            // 비밀번호가 일치하지 않으면 처리할 로직 추가
//            return "redirect:/alheum/senUser/update?error=passwordMismatch";
//        }

        senUserService.updateSenInfo(senUserUpdateDTO);
//        System.out.println(senUserUpdateDTO + "=======");
//        System.out.println(senUserUpdateDTO.getSenMemBirth());

        return "redirect:/alheum/senUser/mypage";
    }

    @GetMapping("/passwordVerify")
    public String passwordVerify(){

        return "basic/passwordVerify-seniorMypage";
    }

    @GetMapping("/checkPassword")
    public String checkPassword(){
        return "basic/passwordVerify-seniorMypage";
    }

    @PostMapping("/checkPassword")
    public String checkPassword(@SessionAttribute("senMemNum") Long senMemNum,
                                @RequestParam("passwordCheck") String passwordCheck){
        String password = senUserService.selectCheckSenPassword(senMemNum);
        return password.equals(passwordCheck) ? "redirect:/alheum/senUser/mypage" : "redirect:/alheum/senUser/checkPassword";
    }
}
