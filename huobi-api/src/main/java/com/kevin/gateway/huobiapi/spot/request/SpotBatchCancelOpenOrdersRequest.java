package com.kevin.gateway.huobiapi.spot.request;

import com.kevin.gateway.huobiapi.base.util.OrderSide;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 批量撤销订单
 */
@Data
public class SpotBatchCancelOpenOrdersRequest {
    @JsonProperty(value = "account-id")
    private String accountId;    //账户ID，取值参考 GET /v1/account/accounts
    private String symbol;    //交易代码列表（最多10 个symbols，多个交易代码间以逗号分隔）
    private OrderSide side;//主动交易方向		“buy”或“sell”，缺省将返回所有符合条件尚未成交订单
    private int size;    //所需返回记录数	100	[0,100]
}
