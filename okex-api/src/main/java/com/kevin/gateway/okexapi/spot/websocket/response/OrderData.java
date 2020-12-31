package com.kevin.gateway.okexapi.spot.websocket.response;

import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.spot.SpotMarketId;
import com.kevin.gateway.okexapi.spot.model.*;
import com.kevin.gateway.okexapi.spot.model.SpotAlgoOrderMode;
import com.kevin.gateway.okexapi.spot.model.SpotOrderInfoState;
import com.kevin.gateway.okexapi.spot.model.SpotPlaceOrderType;
import com.kevin.gateway.okexapi.spot.model.SpotType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * 用户交易数据
 */
@Data
public class OrderData {
    /**
     * 由用户设置的订单ID
     */
    private String clientOid;
    /**
     * 已成交金额
     */
    private BigDecimal filledNotional;
    /**
     * 已成交数量
     */
    private BigDecimal filledSize;
    /**
     * 币对名称
     */
    private SpotMarketId instrumentId;
    /**
     * 成交ID,和交易频道trade_id对应（如果没有，推0）
     */
    private String lastFillId;
    /**
     * 最新成交价格（如果没有，推0）
     */
    private BigDecimal lastFillPx;
    /**
     * 最新成交数量（如果没有，推0）
     */
    private BigDecimal lastFillQty;
    /**
     * 最新成交时间（如果没有，推1970-01-01T00:00:00.000Z）
     */
    private LocalDateTime lastFillTime;
    /**
     * 1：币币交易订单
     * 2：杠杆交易订单
     */
    private SpotAlgoOrderMode marginTrading;
    /**
     * 	买入金额，市价买入时返回
     */
    private BigDecimal notional;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 0：普通委托
     * 1：只做Maker（Post only）
     * 2：全部成交或立即取消（FOK）
     * 3：立即成交并取消剩余（IOC）
     */
    private SpotPlaceOrderType orderType;
    /**
     * 委托价格
     */
    private BigDecimal price;
    /**
     * buy或sell
     */
    private OrderSide side;
    /**
     * 委托数量（交易货币数量）
     */
    private BigDecimal size;

    /**
     * -2:失败
     * -1:撤单成功
     * 0:等待成交
     * 1:部分成交
     * 2:完全成交
     * 3:下单中
     * 4:撤单中
     */
    private SpotOrderInfoState state;

    private String status;
    /**
     * 订单状态更新时间
     */
    private LocalDateTime timestamp;
    /**
     * limit或market（默认是limit）
     */
    private SpotType type;
    /**
     * 订单创建时间
     */
    private LocalDateTime createdAt;
    /**
     * 交易手续费币种，如果是买的话，就是收取的BTC；如果是卖的话，收取的就是USDT
     */
    private String feeCurrency;
    /**
     * 订单交易手续费。平台向用户收取的交易手续费，为负数，例如：-0.01
     */
    private BigDecimal fee;
    /**
     * 返佣金币种 USDT
     */
    private String rebateCurrency;
    /**
     * 返佣金额，平台向达到指定lv交易等级的用户支付的挂单奖励（返佣），如果没有返佣金，该字段为“”，为正数，例如：0.5
     */
    private String rebate;
    /**
     * 相应修改操作的request_id（如没有修改，推""）
     */
    private String lastRequestId;
    /**
     * 相应修改操作的结果，“-1”： 失败，“0”： 成功，"1"：自动撤单（因为修改导致的订单自动撤销）
     */
    private String lastAmendResult;
    /**
     * 	事件代码，（默认为0）
     */
    private String eventCode;
    /**
     * 事件信息，（默认为""）
     */
    private String eventMessage;



}