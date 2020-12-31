package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import lombok.Data;

import java.util.List;

@Data
public class FutureBatchPlaceOrderRequest {


    /**
     * 合约ID，如BTC-USD-180213 ,BTC-USDT-191227
     */
    private FutureMarketId instrumentId;


    /**
     * 批量下单参数
     */
    private List<FuturePlaceOrderRequest> ordersData;

}

