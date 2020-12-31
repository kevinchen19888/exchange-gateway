package com.kevin.gateway.okexapi.future.model;

import lombok.Data;

@Data
public class OneByOneLeverageItem {


    /**
     * 多单 杠杆倍数
     */
    private int longLeverage;


    /**
     * 空单 杠杆倍数
     */
    private int shortLeverage;

}

