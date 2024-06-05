package com.example.menbosa.dto.protector.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Criteria {
    private int page;
    private int amount;

    public Criteria(){
        this(1,10);
    }

    public Criteria(int page, int amount){
        this.page = page;
        this.amount = amount;
    }
}
