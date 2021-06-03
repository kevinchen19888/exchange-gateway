package com.kevin.gateway.huobiapi.spot.request.marginloan;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 申请借币（逐仓）
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotMarginOrderResponse extends SpotBaseResponse {
    private int data;
}
