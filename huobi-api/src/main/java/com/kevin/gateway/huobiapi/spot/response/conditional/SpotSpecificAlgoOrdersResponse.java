package com.kevin.gateway.huobiapi.spot.response.conditional;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 查询特定策略委托
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotSpecificAlgoOrdersResponse extends SpotBaseResponse {
    private SpotHistoryAlgoOrdersResponse.SpotHistoryAlgoOrders data;
}
