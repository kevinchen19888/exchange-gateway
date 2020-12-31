package com.kevin.gateway.okexapi.spot.request;

import com.kevin.gateway.core.CoinPair;
import lombok.Data;

import java.util.List;

/**
 * 撤销订单
 */
@Data
public class SpotCancelOrdersRequest {

    /**
     * 币种
     */
    private CoinPair instrumentId;
}
