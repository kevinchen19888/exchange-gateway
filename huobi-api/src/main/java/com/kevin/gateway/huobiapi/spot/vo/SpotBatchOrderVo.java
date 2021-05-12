package com.kevin.gateway.huobiapi.spot.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kevin.gateway.huobiapi.spot.model.SpotOrdersPlaceType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SpotBatchOrderVo {
    /**
     * 账户 ID，取值参考 GET /v1/account/accounts。现货交易使用 ‘spot’ 账户的 account-id；逐仓杠杆交易，请使用 ‘margin’ 账户的 account-id；
     * 全仓杠杆交易，请使用 ‘super-margin’ 账户的 account-id; C2C杠杆交易，请使用borrow账户的account-id
     */
    @JsonProperty("account-id")
    private String accountId;
    /**
     * 交易对,即btcusdt, ethbtc...（取值参考GET /v1/common/symbols）
     */
    private String symbol;
    /**
     * 订单类型，包括buy-market, sell-market, buy-limit, sell-limit, buy-ioc, sell-ioc, buy-limit-maker, sell-limit-maker（说明见下文）,
     * buy-stop-limit, sell-stop-limit, buy-limit-fok, sell-limit-fok, buy-stop-limit-fok, sell-stop-limit-fok
     */
    private SpotOrdersPlaceType type;
    /**
     * 订单交易量（市价买单为订单交易额）
     */
    private BigDecimal amount;
    private BigDecimal price;
    private String source;
    /**
     * 用户自编订单号（最大长度64个字符，须在24小时内保持唯一性）
     */
    @JsonProperty("client-order-id")
    private String clientOrderId;
    @JsonProperty("stop-price")
    private BigDecimal stopPrice;
    /**
     * 止盈止损订单触发价运算符 gte – greater than and equal (>=), lte – less than and equal (<=)
     */
    private String operator;


}
