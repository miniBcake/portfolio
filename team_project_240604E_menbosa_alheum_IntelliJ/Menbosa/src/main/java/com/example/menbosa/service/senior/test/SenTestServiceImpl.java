package com.example.menbosa.service.senior.test;

import com.example.menbosa.dto.senior.test.SenTestItemDTO;
import com.example.menbosa.dto.senior.test.SenTestListDTO;
import com.example.menbosa.dto.senior.test.SenTestQDTO;
import com.example.menbosa.dto.senior.test.TestResultDTO;
import com.example.menbosa.mapper.senior.test.SenTestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SenTestServiceImpl implements SenTestService{

    private final SenTestMapper senTestMapper;

    @Override
    public List<SenTestListDTO> selectSenTestList() {
        return senTestMapper.selectSenTestList();
    }

    @Override
    public void insertResult(TestResultDTO testResultDTO) {
        senTestMapper.insertResult(testResultDTO);
    }

    @Override
    public List<TestResultDTO> selectResult(Long senMemNum) {
        return senTestMapper.selectResult(senMemNum);
    }

//    @Override
//    public List<SenTestQDTO> selectTestQuest(Long testListNum) {
//        return senTestMapper.selectTestQuest(testListNum);
//    }
//
//    @Override
//    public List<SenTestItemDTO> selectTestItems(Long testQuestNum, Long testListNum) {
//        return senTestMapper.selectTestItems(testQuestNum, testListNum);
//    }

}
