package com.kevin.gateway.okexapi.swap.websocket.response;

import com.kevin.gateway.okexapi.future.type.LastAmendResult;
import com.kevin.gateway.okexapi.future.type.OpenCloseLongShortType;
import com.kevin.gateway.okexapi.future.type.OrderStateType;
import com.kevin.gateway.okexapi.future.type.OrderType;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户交易数据
 */
@Data
public class OrderData {
    /**
     * 最新成交时间（如果没有，推1970-01-01T00:00:00.000Z）
     */
    private LocalDateTime lastFillTime;
    /**
     * 成交数量
     */
    private BigDecimal filledQty;
    /**
     * 手续费
     */
    private BigDecimal fee;
    /**
     * 用户设置的订单id
     */
    private String clientOid;
    /**
     * 最新成交数量（如果没有，推0）
     */
    private BigDecimal lastFillQty;
    /**
     * 成交均价
     */
    private BigDecimal priceAvg;
    /**
     * 1:开多
     * 2:开空
     * 3:平多
     * 4:平空
     */
    private OpenCloseLongShortType type;
    /**
     * 合约名称，如BTC-USD-SWAP
     */
    private SwapMarketId instrumentId;
    /**
     * 最新成交价格（如果没有，推0）
     */
    private BigDecimal lastFillPx;
    /**
     * 最新成交ID (如果没有推0)，和trade 频道推送的trade ID一致
     */
    private String lastFillId;
    /**
     * 委托数量
     */
    private BigDecimal size;
    /**
     * 委托价格
     */
    private BigDecimal price;
    /**
     *
     */
    private String errorCode;
    /**
     * 合约面值
     */
    private String contractVal;
    /**
     * -1:撤单成功
     * 0:等待成交
     * 1:部分成交
     * 2:完全成交
     * 3:下单中
     * 4:撤单中
     */
    private OrderStateType state;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 0：普通委托
     * 1：只做Maker（Post only）
     * 2：全部成交或立即取消（FOK）
     * 3：立即成交并取消剩余（IOC）
     * 4：市价委托
     */
    private OrderType orderType;
    /**
     * 杠杆倍数
     */
    private BigDecimal leverage;
    /**
     * 订单状态变化时间
     */
    private LocalDateTime timestamp;
    /**
     * 相应修改操作的request_id（如没有修改，推""）
     */
    private String lastRequestId;
    /**
     * 相应修改操作的结果，“-1”： 失败，“0”： 成功，"1"：自动撤单（因为修改导致的订单自动撤销）
     */
    private LastAmendResult lastAmendResult;

    private String algoType;

    private String eventCode;

    private String eventMessage;

    private String orderSide;

    private BigDecimal slPrice;

    private BigDecimal slTriggerPrice;

    private String status;

    private BigDecimal tpPrice;

    private BigDecimal tpTriggerPrice;




}
