package com.example.menbosa.controller.protector.inquiry;

import com.example.menbosa.dto.protector.inquiry.ProInqDTO;
import com.example.menbosa.dto.protector.inquiry.ProInqDetailsDTO;
import com.example.menbosa.dto.protector.inqupage.InquCriteria;
import com.example.menbosa.dto.protector.inqupage.InquPage;
import com.example.menbosa.service.protector.inquiry.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/alheum/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    //    1:1문의
    @GetMapping
    public String mypageInquiry(Model model, @SessionAttribute("proMemNum")Long proMemNum, InquCriteria inquCriteria) {
        List<ProInqDTO> proInqList = inquiryService.selectInqPage(proMemNum, inquCriteria);
        int total = inquiryService.selectInquTotal(proMemNum);
        InquPage page = new InquPage(inquCriteria, total);
        model.addAttribute("proInqList", proInqList);
        model.addAttribute("page", page);
        return "/protector/protectorMypage-inquiryList";
    }

    //    1:1문의 상세
    @GetMapping("/details")
    public String mypageInquiryDetails(Model model,
                                       @RequestParam("boardInquNum")long boardInquNum,
                                       @SessionAttribute("proMemNum")Long proMemNum) {
        ProInqDetailsDTO proInqDetails = inquiryService.selectProInqDetails(proMemNum,boardInquNum);
        model.addAttribute("proInqDetails", proInqDetails);
        return "/protector/protectorMypage-inquiryDetails";
    }
    //    1:1문의 글쓰기
    @GetMapping("/write")
    public String mypageInquiryWrite() {
        return "/protector/protectorMypage-inquiryWrite";
    }

    @PostMapping("/write")
    public String mypageInquiryWrite(
            @SessionAttribute("proMemNum") Long proMemNum,
            ProInqDetailsDTO proInqDetailsDTO
    ){
        proInqDetailsDTO.setProMemNum(proMemNum);
        inquiryService.insertInqu(proInqDetailsDTO);
        return "redirect:/alheum/inquiry";
    }
}
