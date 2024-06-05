package com.example.menbosa.controller.protector.communicate;

import com.example.menbosa.dto.protector.communicate.*;
import com.example.menbosa.dto.protector.page.Criteria;
import com.example.menbosa.dto.protector.page.Page;
import com.example.menbosa.service.protector.communicate.CommunicateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/alheum/community")
public class CommunicateController {

    private final CommunicateService communicateService;

    public CommunicateController(CommunicateService communicateService) {
        this.communicateService = communicateService;
    }

    // 공지 목록 페이지
    @GetMapping("/main")
    public String commuAnnounce(Model model, Criteria criteria, HttpSession session) {

        Long proMemNum = (Long) session.getAttribute("proMemNum");

        List<MainListDto> mainList = communicateService.findAllPageAnno(criteria);
        int total = communicateService.findTotalAnno();
        Page page = new Page(criteria, total);

        model.addAttribute("mainList", mainList);
        model.addAttribute("page", page);

        return proMemNum != null ?  "protector/protectorCommunity-announce" : "redirect:/alheum/user/login";
    }

    //  공지 상세 페이지
    @GetMapping("/mainDetails")
    public String commuAnnounceDetails(@RequestParam("announceNum") Long announceNum, Model model){
        MainViewDto mainDetail = communicateService.findByNum(announceNum);
        model.addAttribute("mainDetail", mainDetail);
        return "protector/protectorCommunity-announceDetails";
    }

        // 소통 목록 페이지
    @GetMapping("/commuMain")
    public String commuMain(Model model, Criteria criteria) {
//      List<CommuListDto> commuList = communicateService.selectCommuList();

        List<CommuListDto> commuList = communicateService.findAllPage(criteria);
        int total = communicateService.findTotal();
        Page page = new Page(criteria, total);

        model.addAttribute("commuList", commuList);
        model.addAttribute("page", page);

        return "protector/protectorCommunity-communicateMain";
    }


    // 소통 글쓰기 페이지
    @GetMapping("/commuWrite")
    public String commuWrite(HttpSession session) {

        session.setAttribute("proMemNum", session.getAttribute("proMemNum"));
        return "protector/protectorCommunity-communicateWrite";
    }

    @PostMapping("/commuWrite")
    public String commuWrite(CommuWriteDto commuWriteDto, @SessionAttribute(value = "proMemNum") Long proMemNum,
                             RedirectAttributes redirectAttributes,
                             @RequestParam("boardFile") List<MultipartFile> files
                             ){

        commuWriteDto.setProMemNum(proMemNum);
        try{
            communicateService.registerCommuWithFile(commuWriteDto, files);
        } catch (IOException e) {
            e.printStackTrace();
        }

//            redirectAttributes.addFlashAttribute("boardCommuNum", commuWriteDto.getBoardCommuNum());

            // 작성된 글의 번호를 가져와서 상세 페이지로 리다이렉트
            Long boardCommuNum = commuWriteDto.getBoardCommuNum();
            redirectAttributes.addFlashAttribute("boardCommuNum", boardCommuNum);

            // 상세 페이지 URL로 리다이렉트
            return "redirect:/alheum/community/commuDetails?boardCommuNum=" + boardCommuNum;
        }

        // 소통 상세 페이지
        @GetMapping("/commuDetails")
        public String commuDetails(Long boardCommuNum, Model model){
            CommuDetailDto detail = communicateService.findCommuDetail(boardCommuNum);
            model.addAttribute("detail", detail);
            return "protector/protectorCommunity-communicateDetails";
        }


        //소통 글 수정페이지
        @GetMapping("/commuModify")
        public String commuModify(Long boardCommuNum, Model model){
            CommuDetailDto detail = communicateService.findCommuDetail(boardCommuNum);
            model.addAttribute("detail", detail);

            return "protector/protectorCommunity-retouch";
        }


        @PostMapping("/commuModify")
        public String commuModify(CommuUpdateDto commuUpdateDto,
                                  @RequestParam("boardFile") List<MultipartFile> files,
                                  RedirectAttributes redirectAttributes){

            try{
                communicateService.modifyCommu(commuUpdateDto, files);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Long boardCommuNum = commuUpdateDto.getBoardCommuNum();
            redirectAttributes.addFlashAttribute("boardCommuNum", boardCommuNum);

            return "redirect:/alheum/community/commuDetails?boardCommuNum=" + boardCommuNum;
        }


    // 소통 글 삭제
    @GetMapping("/commuRemove")
    public RedirectView commuRemove(Long boardCommuNum){
        communicateService.removeCommu(boardCommuNum);
        return new RedirectView("/alheum/community/commuMain");
    }


    // 자주하는 질문
    @GetMapping("/commuQuestion")
    public String commuQuestion(){
        return "protector/protectorCommunity-questionMain";
    }



}
