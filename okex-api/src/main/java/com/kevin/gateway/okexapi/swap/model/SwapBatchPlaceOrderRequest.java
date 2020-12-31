package com.kevin.gateway.okexapi.swap.model;

import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.util.List;

@Data
public class SwapBatchPlaceOrderRequest {


    /**
     * 合约ID
     */
    private SwapMarketId instrumentId;


    /**
     * 批量下单参数
     */
    private List<SwapPlaceOrderRequest> ordersData;
}
