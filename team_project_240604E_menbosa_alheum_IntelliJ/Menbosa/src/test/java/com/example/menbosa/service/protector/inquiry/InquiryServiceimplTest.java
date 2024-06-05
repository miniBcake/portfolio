package com.example.menbosa.service.protector.inquiry;

import com.example.menbosa.dto.protector.inquiry.ProInqDetailsDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class InquiryServiceimplTest {

    @Autowired
    private InquiryService inquiryService;
    @Autowired
    private ProInqDetailsDTO proInqDetailsDTO;

    @Test
    void selectProInqTest(){
        System.out.println(inquiryService.selectProInq(600));
    }

    @Test
    void selectProInqDetailsTest(){
        System.out.println(inquiryService.selectProInqDetails(600,600));
    }

    @Test
    void insertInqTest(){
        proInqDetailsDTO = new ProInqDetailsDTO();
        proInqDetailsDTO.setProMemNum(600);
        proInqDetailsDTO.setBoardInquTitle("testtitle");
        proInqDetailsDTO.setBoardInquContents("testcontent");
        System.out.println("testtttttt");
        inquiryService.insertInqu(proInqDetailsDTO);
    }
}