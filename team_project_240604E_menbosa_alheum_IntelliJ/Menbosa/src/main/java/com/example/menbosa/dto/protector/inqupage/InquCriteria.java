package com.example.menbosa.dto.protector.inqupage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class InquCriteria {
    private int page;
    private int amount;
    private long proMemNum;

    public InquCriteria(){
        this(1,10);
    }

    public InquCriteria(int page, int amount){
        this.page = page;
        this.amount = amount;
    }
}
