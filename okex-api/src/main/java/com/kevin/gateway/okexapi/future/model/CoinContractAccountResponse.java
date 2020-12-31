package com.kevin.gateway.okexapi.future.model;


import lombok.Data;

import java.util.Map;


@Data
public class CoinContractAccountResponse {

    private Map<String, CoinContractAccountResponseBase> info;
}
