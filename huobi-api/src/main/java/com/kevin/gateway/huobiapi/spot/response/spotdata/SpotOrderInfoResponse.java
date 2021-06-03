package com.kevin.gateway.huobiapi.spot.response.spotdata;

import com.kevin.gateway.huobiapi.spot.SpotMarketId;
import com.kevin.gateway.huobiapi.spot.model.OrderState;
import com.kevin.gateway.huobiapi.spot.model.SpotOrderOperator;
import com.kevin.gateway.huobiapi.spot.model.SpotOrderSource;
import com.kevin.gateway.huobiapi.spot.model.SpotOrdersPlaceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 查询订单详情
 */
@Data
public class SpotOrderInfoResponse {
    private SpotOrderInfo data;

    @Data
    public static class SpotOrderInfo {
        @JsonProperty(value = "account-id")
        private String accountId;

        /**
         * 用户自编订单号（所有open订单可返回client-order-id（如有）；
         * 仅7天内（基于订单创建时间）的closed订单（state <> canceled）可返回client-order-id（如有）；
         * 仅24小时内（基于订单创建时间）的closed订单（state = canceled）可返回client-order-id（如有））
         */
        @JsonProperty(value = "client-order-id")
        private String clientOrderId;

        private BigDecimal amount;    //订单数量

        @JsonProperty(value = "canceled-at")
        private long canceledAt;    //		订单撤销时间

        @JsonProperty(value = "created-at")
        private long createdAt;    //订单创建时间

        @JsonProperty(value = "field-amount")
        private BigDecimal fieldAmount;//已成交数量

        @JsonProperty(value = "field-cash-amount")
        private BigDecimal fieldCashAmount;//已成交总金额

        @JsonProperty(value = "field-fees")
        private BigDecimal fieldFees;    //已成交手续费（买入为币，卖出为钱）

        @JsonProperty(value = "finished-at")
        private long finishedAt;//订单变为终结态的时间，不是成交时间，包含“已撤单”状态

        @JsonProperty(value = "user-id")
        private String userId;

        private String id;//订单ID

        private BigDecimal price;    //订单价格

        private SpotOrderSource source;//	订单来源	api

        private OrderState state;//订单状态	submitted 已提交, partial-filled 部分成交, partial-canceled 部分成交撤销, filled 完全成交, canceled 已撤销， created

        private SpotMarketId symbol;//交易对	btcusdt, ethbtc, rcneth ...

        private SpotOrdersPlaceType type;//	订单类型	buy-market：市价买, sell-market：市价卖, buy-limit：限价买, sell-limit：限价卖, buy-ioc：IOC买单, sell-ioc：IOC卖单， buy-limit-maker, sell-limit-maker, buy-stop-limit，sell-stop-limit, buy-limit-fok, sell-limit-fok, buy-stop-limit-fok, sell-stop-limit-fok

        @JsonProperty(value = "stop-pricet")
        private long stopPrice;//止盈止损订单触发价格

        private SpotOrderOperator operator;//	止盈止损订单触发价运算符	gte,lte
    }
}
