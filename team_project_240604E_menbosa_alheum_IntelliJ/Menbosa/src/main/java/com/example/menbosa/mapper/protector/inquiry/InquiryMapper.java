package com.example.menbosa.mapper.protector.inquiry;

import com.example.menbosa.dto.protector.inquiry.ProInqDTO;
import com.example.menbosa.dto.protector.inquiry.ProInqDetailsDTO;
import com.example.menbosa.dto.protector.inqupage.InquCriteria;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface InquiryMapper {
    List<ProInqDTO> selectProInq(long proMemNum);

    List<ProInqDTO> selectInqPage(InquCriteria inqucriteria);

    ProInqDetailsDTO selectProInqDetails(long proMemNum, long boardInquNum);

    void insertInqu(ProInqDetailsDTO proInqDetailsDTO);

    int selectInquTotal(Long proMemNum);
}
