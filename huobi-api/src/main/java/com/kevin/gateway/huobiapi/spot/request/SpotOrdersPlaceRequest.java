package com.kevin.gateway.huobiapi.spot.request;

import com.kevin.gateway.huobiapi.spot.SpotMarketId;
import com.kevin.gateway.huobiapi.spot.model.SpotOrderOperator;
import com.kevin.gateway.huobiapi.spot.model.SpotOrderSource;
import com.kevin.gateway.huobiapi.spot.model.SpotOrdersPlaceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 下单
 */
@Data
public class SpotOrdersPlaceRequest {
    /**
     * 账户 ID，取值参考 GET /v1/account/accounts。
     * 现货交易使用 ‘spot’ 账户的 account-id；
     * 逐仓杠杆交易，请使用 ‘margin’ 账户的 account-id；
     * 全仓杠杆交易，请使用 ‘super-margin’ 账户的 account-id
     */
    @JsonProperty(value = "account-id")
    private String accountId;

    /**
     * 交易对,即btcusdt, ethbtc...（取值参考GET /v1/common/symbols）
     */
    private SpotMarketId symbol;
    private SpotOrdersPlaceType type;//订单类型
    private BigDecimal amount;//订单交易量（市价买单为订单交易额）
    private BigDecimal price;//订单价格（对市价单无效）

    /**
     * 现货交易填写“spot-api”，
     * 逐仓杠杆交易填写“margin-api”，
     * 全仓杠杆交易填写“super-margin-api”,
     * C2C杠杆交易填写"c2c-margin-api"
     */
    private SpotOrderSource source;
    @JsonProperty(value = "client-order-id")
    private String clientOrderId;    //	用户自编订单号（最大长度64个字符，须在24小时内保持唯一性）
    @JsonProperty(value = "stop-price")
    private BigDecimal stopPrice;    //	止盈止损订单触发价格

    /**
     * 止盈止损订单触发价运算符
     * gte – greater than and equal (>=),
     * lte – less than and equal (<=)
     */
    private SpotOrderOperator operator;
}
