package com.example.menbosa.service.protector.inquiry;

import com.example.menbosa.dto.protector.inquiry.ProInqDTO;
import com.example.menbosa.dto.protector.inquiry.ProInqDetailsDTO;
import com.example.menbosa.dto.protector.inqupage.InquCriteria;

import java.util.List;

public interface InquiryService {
    List<ProInqDTO> selectProInq(long proMemNum);

    List<ProInqDTO> selectInqPage(long proMemNum, InquCriteria inqucriteria);

    ProInqDetailsDTO selectProInqDetails(long proMemNum, long boardInquNum);

    void insertInqu(ProInqDetailsDTO proInqDetailsDTO);

    int selectInquTotal(Long proMemNum);
}
