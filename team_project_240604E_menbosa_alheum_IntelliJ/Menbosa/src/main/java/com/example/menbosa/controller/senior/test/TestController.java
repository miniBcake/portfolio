package com.example.menbosa.controller.senior.test;

import com.example.menbosa.dto.senior.test.SenTestItemDTO;
import com.example.menbosa.dto.senior.test.SenTestListDTO;
import com.example.menbosa.dto.senior.test.SenTestQDTO;
import com.example.menbosa.dto.senior.test.TestResultDTO;
import com.example.menbosa.service.senior.test.SenTestService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/alheum/senTest")
public class TestController {

    private int resultSum;

    private final SenTestService senTestService;

    @GetMapping("/testList")
    public String testList(Model model){
       List<SenTestListDTO> senTestList =  senTestService.selectSenTestList();
       model.addAttribute("senTestList", senTestList);
        return "senior/seniorTest-list";
    }

    @GetMapping("/testStart")
    public String testStart(@RequestParam("testListNum")Long testListNum, HttpSession session, Model model){
        model.addAttribute("testListNum", testListNum);

        if (testListNum == 600) {
            session.setAttribute("testListNum", 600);
            return "senior/seniorTest1-start";
        } else if (testListNum == 601) {
            session.setAttribute("testListNum", 601);
            return "senior/seniorTest2-start";
        } else if (testListNum == 602) {
            session.setAttribute("testListNum", 602);
            return "senior/seniorTest3-start";
        }

        return "redirect:/alheum/senTest/testList";
    }

    @PostMapping("/processForm11")
    public String processForm11( @RequestParam("test1-1-radio") String radioValue, Model model) {
        // hiddenField 값과 radioValue를 사용하여 필요한 로직 수행

        System.out.println("Radio Value: " + radioValue);

        // 필요한 경우 모델에 데이터 추가
        model.addAttribute("radioValue", radioValue);

        this.resultSum += Integer.parseInt(radioValue);
        System.out.println(resultSum);

        // 다음 페이지로 리다이렉트
        return "redirect:/alheum/senTest/test1-2";
    }

    @PostMapping("/processForm12")
    public String processForm12( @RequestParam("test1-2-radio") String radioValue, Model model) {
        // hiddenField 값과 radioValue를 사용하여 필요한 로직 수행
        System.out.println("Radio Value: " + radioValue);

        // 필요한 경우 모델에 데이터 추가
        model.addAttribute("radioValue", radioValue);

        this.resultSum += Integer.parseInt(radioValue);
        System.out.println(resultSum);

        // 다음 페이지로 리다이렉트
        return "redirect:/alheum/senTest/test1-3";
    }

    @PostMapping("/processForm13")
    public String processForm13( @RequestParam("test1-3-radio") String radioValue, Model model) {
        // hiddenField 값과 radioValue를 사용하여 필요한 로직 수행

        System.out.println("Radio Value: " + radioValue);

        // 필요한 경우 모델에 데이터 추가
        model.addAttribute("radioValue", radioValue);

        this.resultSum += Integer.parseInt(radioValue);
        System.out.println(resultSum);

        // 다음 페이지로 리다이렉트
        return "redirect:/alheum/senTest/test1-4";
    }

    @PostMapping("/processForm14")
    public String processForm14(@RequestParam("test1-4-radio") String radioValue, Model model) {
        // hiddenField 값과 radioValue를 사용하여 필요한 로직 수행
        System.out.println("Radio Value: " + radioValue);

        // 필요한 경우 모델에 데이터 추가
        model.addAttribute("radioValue", radioValue);

        this.resultSum += Integer.parseInt(radioValue);
        System.out.println(resultSum);

        // 다음 페이지로 리다이렉트
        return "redirect:/alheum/senTest/test1-5";
    }

    @PostMapping("/processForm15")
    public String processForm15(@RequestParam("test1-5-radio") String radioValue,
                                @SessionAttribute("senMemNum") Long senMemNum,
                                @SessionAttribute("testListNum") Long testListNum,
                                TestResultDTO testResultDTO, Model model) {
        // hiddenField 값과 radioValue를 사용하여 필요한 로직 수행
        System.out.println("Radio Value: " + radioValue);

        this.resultSum += Integer.parseInt(radioValue);
        System.out.println(resultSum);

        testResultDTO.setTestResultScore(resultSum);
        testResultDTO.setSenMemNum(senMemNum);
        testResultDTO.setTestListNum(testListNum);

        System.out.println(testResultDTO);

        senTestService.insertResult(testResultDTO);



        // 다음 페이지로 리다이렉트
        return "redirect:/alheum/senTest/test1-5?showModal=true";
    }

//    ====================================================================

    @PostMapping("/processForm21")
    public String processForm21( @RequestParam("test2-1-radio") String radioValue, Model model) {
        // hiddenField 값과 radioValue를 사용하여 필요한 로직 수행

        System.out.println("Radio Value: " + radioValue);

        // 필요한 경우 모델에 데이터 추가
        model.addAttribute("radioValue", radioValue);

        this.resultSum += Integer.parseInt(radioValue);
        System.out.println(resultSum);

        // 다음 페이지로 리다이렉트
        return "redirect:/alheum/senTest/test2-2";
    }
    @PostMapping("/processForm22")
    public String processForm22( @RequestParam("test2-2-radio") String radioValue, Model model) {
        // hiddenField 값과 radioValue를 사용하여 필요한 로직 수행

        System.out.println("Radio Value: " + radioValue);

        // 필요한 경우 모델에 데이터 추가
        model.addAttribute("radioValue", radioValue);

        this.resultSum += Integer.parseInt(radioValue);
        System.out.println(resultSum);

        // 다음 페이지로 리다이렉트
        return "redirect:/alheum/senTest/test2-3";
    }
    @PostMapping("/processForm23")
    public String processForm23( @RequestParam("test2-3-radio") String radioValue, Model model) {
        // hiddenField 값과 radioValue를 사용하여 필요한 로직 수행

        System.out.println("Radio Value: " + radioValue);

        // 필요한 경우 모델에 데이터 추가
        model.addAttribute("radioValue", radioValue);

        this.resultSum += Integer.parseInt(radioValue);
        System.out.println(resultSum);

        // 다음 페이지로 리다이렉트
        return "redirect:/alheum/senTest/test2-4";
    }
    @PostMapping("/processForm24")
    public String processForm24( @RequestParam("test2-4-radio") String radioValue, Model model) {
        // hiddenField 값과 radioValue를 사용하여 필요한 로직 수행

        System.out.println("Radio Value: " + radioValue);

        // 필요한 경우 모델에 데이터 추가
        model.addAttribute("radioValue", radioValue);

        this.resultSum += Integer.parseInt(radioValue);
        System.out.println(resultSum);

        // 다음 페이지로 리다이렉트
        return "redirect:/alheum/senTest/test2-5";
    }
    @PostMapping("/processForm25")
    public String processForm25(@RequestParam("test2-5-radio") String radioValue,
                                @SessionAttribute("senMemNum") Long senMemNum,
                                @SessionAttribute("testListNum") Long testListNum,
                                TestResultDTO testResultDTO, Model model) {
        // hiddenField 값과 radioValue를 사용하여 필요한 로직 수행
        System.out.println("Radio Value: " + radioValue);

        this.resultSum += Integer.parseInt(radioValue);
        System.out.println(resultSum);

        testResultDTO.setTestResultScore(resultSum);
        testResultDTO.setSenMemNum(senMemNum);
        testResultDTO.setTestListNum(testListNum);

        System.out.println(testResultDTO);

        senTestService.insertResult(testResultDTO);

        // 검사 결과를 다시 가져와서 모델에 추가
        List<TestResultDTO> selectResult = senTestService.selectResult(senMemNum);
        model.addAttribute("selectResult", selectResult);

        // 다음 페이지로 리다이렉트
        return "redirect:/alheum/senTest/test2-5?showModal=true";
    }

    //    ====================================================================

    @PostMapping("/processForm31")
    public String processForm31( @RequestParam("test3-1-1-radio") String radioValue, Model model) {
        // hiddenField 값과 radioValue를 사용하여 필요한 로직 수행

        System.out.println("Radio Value: " + radioValue);

        // 필요한 경우 모델에 데이터 추가
        model.addAttribute("radioValue", radioValue);

        this.resultSum += Integer.parseInt(radioValue);
        System.out.println(resultSum);

        // 다음 페이지로 리다이렉트
        return "redirect:/alheum/senTest/test3-1-2";
    }
    @PostMapping("/processForm32")
    public String processForm32( @RequestParam("test3-1-2-radio") String radioValue, Model model) {
        // hiddenField 값과 radioValue를 사용하여 필요한 로직 수행

        System.out.println("Radio Value: " + radioValue);

        // 필요한 경우 모델에 데이터 추가
        model.addAttribute("radioValue", radioValue);

        this.resultSum += Integer.parseInt(radioValue);
        System.out.println(resultSum);

        // 다음 페이지로 리다이렉트
        return "redirect:/alheum/senTest/test3-2";
    }
    @PostMapping("/processForm33")
    public String processForm33( @RequestParam("test3-2-1-radio") String radioValue, Model model) {
        // hiddenField 값과 radioValue를 사용하여 필요한 로직 수행

        System.out.println("Radio Value: " + radioValue);

        // 필요한 경우 모델에 데이터 추가
        model.addAttribute("radioValue", radioValue);

        this.resultSum += Integer.parseInt(radioValue);
        System.out.println(resultSum);

        // 다음 페이지로 리다이렉트
        return "redirect:/alheum/senTest/test3-2-2";
    }
    @PostMapping("/processForm34")
    public String processForm34( @RequestParam("test3-2-2-radio") String radioValue, Model model) {
        // hiddenField 값과 radioValue를 사용하여 필요한 로직 수행

        System.out.println("Radio Value: " + radioValue);

        // 필요한 경우 모델에 데이터 추가
        model.addAttribute("radioValue", radioValue);

        this.resultSum += Integer.parseInt(radioValue);
        System.out.println(resultSum);

        // 다음 페이지로 리다이렉트
        return "redirect:/alheum/senTest/test3-3";
    }
    @PostMapping("/processForm35")
    public String processForm35(@RequestParam("test3-3-1-radio") String radioValue,
                                @SessionAttribute("senMemNum") Long senMemNum,
                                @SessionAttribute("testListNum") Long testListNum,
                                TestResultDTO testResultDTO, Model model) {
        // hiddenField 값과 radioValue를 사용하여 필요한 로직 수행
        System.out.println("Radio Value: " + radioValue);

        this.resultSum += Integer.parseInt(radioValue);
        System.out.println(resultSum);

        testResultDTO.setTestResultScore(resultSum);
        testResultDTO.setSenMemNum(senMemNum);
        testResultDTO.setTestListNum(testListNum);

        System.out.println(testResultDTO);

        senTestService.insertResult(testResultDTO);

        // 검사 결과를 다시 가져와서 모델에 추가
        List<TestResultDTO> selectResult = senTestService.selectResult(senMemNum);

        model.addAttribute("selectResult", selectResult);


        // 다음 페이지로 리다이렉트
        return "redirect:/alheum/senTest/test3-3-1?showModal=true";
    }



    @GetMapping("/test1-1")
    public String test11(){
        return "senior/seniorTest1-1";
    }
    @GetMapping("/test1-2")
    public String test12(){
        return "senior/seniorTest1-2";
    }
    @GetMapping("/test1-3")
    public String test13(){
        return "senior/seniorTest1-3";
    }
    @GetMapping("/test1-4")
    public String test14(){
        return "senior/seniorTest1-4";
    }
    @GetMapping("/test1-5")
    public String test15(Model model, @SessionAttribute("senMemNum") Long senMemNum){
        // 검사 결과를 다시 가져와서 모델에 추가
        List<TestResultDTO> selectResult = senTestService.selectResult(senMemNum);
        model.addAttribute("selectResult", selectResult);
        return "senior/seniorTest1-5";
    }

    @GetMapping("/test2-1")
    public String test21(){
        return "senior/seniorTest2-1";
    }
    @GetMapping("/test2-2")
    public String test22(){
        return "senior/seniorTest2-2";
    }
    @GetMapping("/test2-3")
    public String test23(){
        return "senior/seniorTest2-3";
    }
    @GetMapping("/test2-4")
    public String test24(){
        return "senior/seniorTest2-4";
    }
    @GetMapping("/test2-5")
    public String test25(Model model, @SessionAttribute("senMemNum") Long senMemNum){
        List<TestResultDTO> selectResult = senTestService.selectResult(senMemNum);
        model.addAttribute("selectResult", selectResult);
        return "senior/seniorTest2-5";
    }

    @GetMapping("/test3-1")
    public String test31(){
        return "senior/seniorTest3-1";
    }
    @GetMapping("/test3-1-1")
    public String test311(){
        return "senior/seniorTest3-1-1";
    }
    @GetMapping("/test3-1-2")
    public String test312(){
        return "senior/seniorTest3-1-2";
    }
    @GetMapping("/test3-2")
    public String test32(){
        return "senior/seniorTest3-2";
    }
    @GetMapping("/test3-2-1")
    public String test321(){
        return "senior/seniorTest3-2-1";
    }
    @GetMapping("/test3-2-2")
    public String test322(){
        return "senior/seniorTest3-2-2";
    }
    @GetMapping("/test3-3")
    public String test33(){
        return "senior/seniorTest3-3";
    }
    @GetMapping("/test3-3-1")
    public String test331(Model model, @SessionAttribute("senMemNum") Long senMemNum){
        List<TestResultDTO> selectResult = senTestService.selectResult(senMemNum);
        model.addAttribute("selectResult", selectResult);
        return "senior/seniorTest3-3-1";
    }
}
