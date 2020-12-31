package com.kevin.gateway.huobiapi.spot.response.spotAndMargin;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.OrderState;
import com.kevin.gateway.huobiapi.spot.model.SpotOrderSource;
import com.kevin.gateway.huobiapi.spot.model.SpotOrdersPlaceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查询未完成订单
 */
@Data
public class SpotOpenOrdersResponse {
    private List<SpotOpenOrders> data;

    @Data
    private static class SpotOpenOrders {
        private String id;    //订单id，无大小顺序，可作为下一次翻页查询请求的from字段
        @JsonProperty(value = "account-id")
        private String accountId;
        @JsonProperty(value = "client-order-id")
        private String clientOrderId;    //用户自编订单号（所有open订单可返回client-order-id）
        private SpotCoin symbol;    //	交易对, 例如btcusdt, ethbtc
        private BigDecimal price;    //	limit order的交易价格
        private BigDecimal amount;    //	limit order的交易价格
        @JsonProperty(value = "created-at")
        private long createdAt;    //	订单创建的调整为新加坡时间的时间戳，单位毫秒
        private SpotOrdersPlaceType type;    //	订单类型
        @JsonProperty(value = "filled-amount")
        private BigDecimal filledAmount;    //	订单中已成交部分的数量
        @JsonProperty(value = "filled-cash-amount")
        private BigDecimal filledCashAmount;    //	订单中已成交部分的总价格
        @JsonProperty(value = "filled-fees")
        private BigDecimal filledFees;    //	已交交易手续费总额
        private SpotOrderSource source;    //	现货交易填写“api”
        private OrderState state;    //	订单状态，包括submitted, partial-filled, cancelling, created
        @JsonProperty(value = "stop-price")
        private BigDecimal stopPrice;    //	止盈止损订单触发价格
        private String operator;    //	止盈止损订单触发价运算符
    }
}
