package com.kevin.gateway.okexapi.future.model;


import com.kevin.gateway.okexapi.future.type.AutoMarginStatus;
import lombok.Data;

import java.util.List;

@Data
public class CoinContractAccountResponseFixed extends CoinContractAccountResponseBase {

    private List<CoinContractAccountContractResponse> contracts;


    /**
     * 是否自动追加保证金
     * 1: 自动追加已开启
     * 0: 自动追加未开启
     */
    private AutoMarginStatus autoMargin;


}
