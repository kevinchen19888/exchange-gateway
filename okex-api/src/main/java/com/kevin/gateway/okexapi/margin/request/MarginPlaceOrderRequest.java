package com.kevin.gateway.okexapi.margin.request;

import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.margin.MarginMarketId;
import com.kevin.gateway.okexapi.margin.model.MarginTrading;
import com.kevin.gateway.okexapi.spot.model.SpotType;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 下单参数实体
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = MarginPlaceOrderRequest.MarginPlaceLimitOrderRequest.class, name = "limit"),
        @JsonSubTypes.Type(value = MarginPlaceOrderRequest.MarginPlaceMarketOrderRequest.class, name = "market"),
})
public abstract class MarginPlaceOrderRequest {
    /**
     * 币对名称
     */
    private MarginMarketId instrumentId;
    /**
     * 由您设置的订单ID来识别您的订单,格式是字母（区分大小写）+数字 或者 纯字母（区分大小写），1-32位字符 （不能重复）
     */
    private String clientOid;
    /**
     * 买卖类型 buy/sell
     */
    private OrderSide side;

    /**
     * 参数填数字
     * 0：普通委托（order type不填或填0都是普通委托）
     * 1：只做Maker（Post only）
     * 2：全部成交或立即取消（FOK）
     * 3：立即成交并取消剩余（IOC）
     */
    private MarginPlaceOrderType orderType;
    /**
     * 下单类型（当前为币币杠杆交易，请求值为2）
     */
    private MarginTrading marginTrading;

    public abstract String getType();

    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    public static class MarginPlaceMarketOrderRequest extends MarginPlaceOrderRequest {
        /**
         * 卖出数量，市价卖出时必填size
         */
        private BigDecimal size;
        /**
         * 买入金额，市价买入时必填notional
         */
        private BigDecimal notional;

        @Override
        public String getType() {
            return SpotType.MARKET.getType();
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    public static class MarginPlaceLimitOrderRequest extends MarginPlaceOrderRequest {
        /**
         * 买入或卖出的数量
         */
        private BigDecimal size;
        /**
         * 价格
         */
        private BigDecimal price;

        @Override
        public String getType() {
            return SpotType.LIMIT.getType();
        }
    }
}
