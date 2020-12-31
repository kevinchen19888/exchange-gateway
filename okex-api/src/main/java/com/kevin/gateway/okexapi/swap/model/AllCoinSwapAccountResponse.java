package com.kevin.gateway.okexapi.swap.model;


import lombok.Data;

import java.util.List;

@Data
public class AllCoinSwapAccountResponse {

    private List<CoinSwapAccountResponse> info;
}
