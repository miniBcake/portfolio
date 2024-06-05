package com.example.menbosa.service.senior.test;

import com.example.menbosa.dto.senior.test.SenTestItemDTO;
import com.example.menbosa.dto.senior.test.SenTestListDTO;
import com.example.menbosa.dto.senior.test.SenTestQDTO;
import com.example.menbosa.dto.senior.test.TestResultDTO;

import java.util.List;

public interface SenTestService {
    List<SenTestListDTO> selectSenTestList();

//    List<SenTestQDTO> selectTestQuest(Long testListNum);
//
//    List<SenTestItemDTO> selectTestItems(Long testQuestNum, Long testListNum);

    void insertResult(TestResultDTO testResultDTO);

    List<TestResultDTO> selectResult(Long senMemNum);
}
