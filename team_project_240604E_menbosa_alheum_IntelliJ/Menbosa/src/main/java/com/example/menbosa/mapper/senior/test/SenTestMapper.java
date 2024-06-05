package com.example.menbosa.mapper.senior.test;

import com.example.menbosa.dto.senior.test.SenTestItemDTO;
import com.example.menbosa.dto.senior.test.SenTestListDTO;
import com.example.menbosa.dto.senior.test.SenTestQDTO;
import com.example.menbosa.dto.senior.test.TestResultDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface SenTestMapper {

    List<SenTestListDTO> selectSenTestList();

//    List<SenTestQDTO> selectTestQuest(Long testListNum);
//
//    List<SenTestItemDTO> selectTestItems(Long testQuestNum, Long testListNum);

    void insertResult(TestResultDTO testResultDTO);

    List<TestResultDTO> selectResult(Long senMemNum);
}
