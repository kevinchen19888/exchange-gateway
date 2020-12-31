package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.core.CoinPair;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 批量下单请求
 */
@Data
@AllArgsConstructor
public class OptionBatchOrderRequest {
    /**
     * 标的指数，如BTC-USD，同一批订单合约必须针对同一指数标的
     */
    private CoinPair underlying;
    /**
     * 订单请求列表，每个请求与单个订单请求格式一致
     */
    private List<OptionOrderRequest> orderData;
}
