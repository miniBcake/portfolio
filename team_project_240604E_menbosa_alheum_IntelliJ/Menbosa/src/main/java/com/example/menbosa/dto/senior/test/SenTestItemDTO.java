package com.example.menbosa.dto.senior.test;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class SenTestItemDTO {


    private String testItemOne;
    private String testItemTwo;
    private String testItemThree;
    private String testItemFour;
    private List<SenTestItemDTO> itemList;
}
