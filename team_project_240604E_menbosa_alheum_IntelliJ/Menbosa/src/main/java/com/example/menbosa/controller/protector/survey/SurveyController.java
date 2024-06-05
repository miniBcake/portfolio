package com.example.menbosa.controller.protector.survey;

import com.example.menbosa.dto.protector.inquiry.ProInqDTO;
import com.example.menbosa.dto.protector.survey.ProSurveyQDTO;
import com.example.menbosa.service.protector.survey.SurveyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequestMapping("/alheum/survey")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;

    @GetMapping
    public String survey(@SessionAttribute(value="proMemNum", required = false) Long proMemNum) {
        return proMemNum == null ? "/basic/division" : "/basic/division-member";
    }

    @GetMapping("/test")
    public String testSurvey(Model model) {
        List<ProSurveyQDTO> SurveyQList = surveyService.selectSurveyQ();
        model.addAttribute("SurveyQList", SurveyQList);
        return "/protector/protectorTest-survey";
    }

    @PostMapping("/test")
    public void saveResult(@SessionAttribute("proMemNum")Long proMemNum, Long resultSurveyScore){
        System.out.println(resultSurveyScore);
        surveyService.insertSurvey(resultSurveyScore, proMemNum);
    }
}
