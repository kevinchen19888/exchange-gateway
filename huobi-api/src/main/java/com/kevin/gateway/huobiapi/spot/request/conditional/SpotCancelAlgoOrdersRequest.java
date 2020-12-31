package com.kevin.gateway.huobiapi.spot.request.conditional;

import lombok.Data;

/**
 * 策略委托（触发前）撤单
 */
@Data
public class SpotCancelAlgoOrdersRequest {
    private String[] clientOrderIds;//用户自编订单号（可多填，以逗号分隔）
}
