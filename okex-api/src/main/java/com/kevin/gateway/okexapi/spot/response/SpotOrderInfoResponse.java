package com.kevin.gateway.okexapi.spot.response;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.spot.model.SpotOrderInfoState;
import com.kevin.gateway.okexapi.spot.model.SpotPlaceOrderType;
import com.kevin.gateway.okexapi.spot.model.SpotType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单列表\获取所有未成交订单\获取订单信息
 * 响应实体
 */
@Data
public class SpotOrderInfoResponse {
    /**
     * 订单id
     */
    private String orderId;

    private String clientOid;
    /**
     * limit 订单类型的价格信息
     */
    private BigDecimal price;
    /**
     * 委托数量
     */
    private BigDecimal size;
    /**
     * market 订单类型的价格信息
     */
    private BigDecimal notional;
    /**
     * 币对信息
     */
    private CoinPair instrumentId;
    /**
     * 订单类型 limit/market
     */
    private SpotType type;
    /**
     * 订单买卖类型 buy/sell
     */
    private OrderSide side;
    /**
     * 委托时间
     */
    private LocalDateTime timestamp;
    /**
     * 已成交数量
     */
    private BigDecimal filledSize;

    /**
     * 已成交金额
     */
    private BigDecimal filledNotional;

    /**
     * 0：普通委托
     * 1：只做Maker（Post only）
     * 2：全部成交或立即取消（FOK）
     * 3：立即成交并取消剩余（IOC）
     */
    private SpotPlaceOrderType orderType;
    /**
     * 订单状态
     * -2:失败
     * -1:撤单成功
     * 0:等待成交
     * 1:部分成交
     * 2:完全成交
     * 3:下单中
     * 4:撤单中
     */
    private SpotOrderInfoState state;
    /**
     * 平均成交价
     */
    private BigDecimal priceAvg;

    /**
     * 交易手续费币种，如果是买的话，就是收取的BTC；如果是卖的话，收取的就是USDT
     * todo:数据为空的情况
     */
    private String feeCurrency;

    /**
     * 订单交易手续费。平台向用户收取的交易手续费，为负数，例如：-0.01
     */
    private BigDecimal fee;

    /**
     * 返佣金币种 todo:数据为空的情况
     */
    private String rebateCurrency;

    /**
     * 返佣金额，平台向达到指定lv交易等级的用户支付的挂单奖励（返佣），如果没有返佣金，该字段为“”，为正数，例如：0.5
     */
    private BigDecimal rebate;

    /**
     * 以下字段为多余字段
     */
    private LocalDateTime createdAt;

    private String funds;

    private String productId;

    private String status;

}
