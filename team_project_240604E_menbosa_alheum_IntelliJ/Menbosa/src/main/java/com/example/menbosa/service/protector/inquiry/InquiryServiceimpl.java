package com.example.menbosa.service.protector.inquiry;

import com.example.menbosa.dto.protector.inquiry.ProInqDTO;
import com.example.menbosa.dto.protector.inquiry.ProInqDetailsDTO;
import com.example.menbosa.dto.protector.inqupage.InquCriteria;
import com.example.menbosa.mapper.protector.inquiry.InquiryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryServiceimpl implements InquiryService{

    private final InquiryMapper inquiryMapper;

    @Override
    public List<ProInqDTO> selectProInq(long proMemNum) {
        return inquiryMapper.selectProInq(proMemNum);
    }

    @Override
    public List<ProInqDTO> selectInqPage(long proMemNum, InquCriteria inqucriteria) {
        inqucriteria.setProMemNum(proMemNum);
        return inquiryMapper.selectInqPage(inqucriteria);
    }

    @Override
    public ProInqDetailsDTO selectProInqDetails(long proMemNum, long boardInquNum) {
        return inquiryMapper.selectProInqDetails(proMemNum, boardInquNum);
    }

    @Override
    public void insertInqu(ProInqDetailsDTO proInqDetailsDTO) {
        inquiryMapper.insertInqu(proInqDetailsDTO);
    }

    @Override
    public int selectInquTotal(Long proMemNum) {
        return inquiryMapper.selectInquTotal(proMemNum);
    }
}
