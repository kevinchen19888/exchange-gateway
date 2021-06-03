package com.kevin.gateway.huobiapi.spot.response.spotdata;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 搜索历史订单
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotHistoryOrdersResponse extends SpotBaseResponse {
    private List<SpotOrderInfoResponse.SpotOrderInfo> data;
}
