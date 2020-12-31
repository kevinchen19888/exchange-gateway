package com.kevin.gateway.huobiapi.spot.response.spotAndMargin;

import com.kevin.gateway.huobiapi.spot.SpotMarketId;
import com.kevin.gateway.huobiapi.spot.model.SpotOrderRole;
import com.kevin.gateway.huobiapi.spot.model.SpotOrderSource;
import com.kevin.gateway.huobiapi.spot.model.SpotOrdersPlaceType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 成交明细
 */
@Data
public class SpotMatchResultsResponse {
    private List<SpotMatchResults> data;

    @Data
    private static class SpotMatchResults {
        @JsonProperty(value = "created-at")
        private long createdAt;    //成交时间戳timestamp

        @JsonProperty(value = "filled-amount")
        private BigDecimal filledAmount;//	成交数量

        @JsonProperty(value = "filled-fees")
        private BigDecimal filledFees;    //	交易手续费（正值）或交易返佣金（负值）

        @JsonProperty(value = "fee-currency")
        private BigDecimal feeCurrency;//交易手续费或交易返佣币种（买单的交易手续费币种为基础币种，卖单的交易手续费币种为计价币种；买单的交易返佣币种为计价币种，卖单的交易返佣币种为基础币种）

        private String id;//订单成交记录ID

        @JsonProperty(value = "match-id")
        private String matchId;//撮合ID，订单在撮合中执行的顺序ID

        @JsonProperty(value = "order-id")
        private String orderId;    //订单ID，成交所属订单的ID

        @JsonProperty(value = "trade-id")
        private String tradeId;//Unique trade ID (NEW)唯一成交编号，成交时产生的唯一编号ID

        private BigDecimal price;//	成交价格

        private SpotOrderSource source;//	订单来源	api

        private SpotMarketId symbol;//交易对	btcusdt, ethbtc, rcneth ...

        private SpotOrdersPlaceType type;//订单类型	buy-market：市价买, sell-market：市价卖, buy-limit：限价买, sell-limit：限价卖, buy-ioc：IOC买单, sell-ioc：IOC卖单， buy-limit-maker, sell-limit-maker, buy-stop-limit，sell-stop-limit, buy-limit-fok, sell-limit-fok, buy-stop-limit-fok, sell-stop-limit-fok

        private SpotOrderRole role;    //	成交角色	maker,taker

        @JsonProperty(value = "filled-points")
        private BigDecimal filledPoints;//	抵扣数量（可为ht或hbpoint）

        @JsonProperty(value = "fee-deduct-currency")
        private String feeDeductCurrency;//	抵扣类型	如果为空，代表扣除的手续费是原币；如果为"ht"，代表抵扣手续费的是HT；如果为"hbpoint"，代表抵扣手续费的是点卡
    }
}
