package com.kevin.gateway.okexapi.option.websocket.response;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.kevin.gateway.okexapi.option.websocket.response.type.OkexOptionOrderStatus;
import com.kevin.gateway.okexapi.option.websocket.response.type.OkexOptionOrderType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 用户交易数据
 */
@Data
public class OrderData {
    /**
     * 由您设置的订单ID来识别您的订单
     */
    private String clientOid;
    /**
     * 合约面值（即合约乘数）
     */
    private BigDecimal contractVal;
    /**
     * 事件代码（默认为0）
     */
    private String eventCode;
    /**
     * 事件信息（默认为""）
     */
    private String eventMessage;
    /**
     * 手续费
     */
    private BigDecimal fee;
    /**
     * 成交数量
     */
    private BigDecimal filledQty;
    /**
     * 合约名称，如BTC-USD-190927-12500-C
     */
    private OptionMarketId instrumentId;
    /**
     * 相应修改操作的结果，“-1”： 失败，“0”： 成功，"1"：自动撤单（因为修改导致的订单自动撤销）1.如果API用户的订单cancel_on_fail设置为0或者是web/APP撤单，则修改失败后，last_amend_result返回 “-1” 2.如果API用户的订单cancel_on_fail设置为1，则修改失败后，last_amend_result返回“ 1”
     */
    private String lastAmendResult;
    /**
     * 最新成交id（如果没有，推0），和trade频道推送的trade_id一致
     */
    private String lastFillId;
    /**
     * 最新成交价格（如没有成交，为0）
     */
    private BigDecimal lastFillPx;
    /**
     * 最新成交数量（如没有成交，为0）
     */
    private BigDecimal lastFillQty;
    /**
     *
     */
    private LocalDateTime lastFillTime;
    /**
     * 相应修改操作的request_id（如没有修改，推""）
     */
    private String lastRequestId;
    /**
     * 订单ID
     */
    private String orderId;
    /**
     * 	0：普通委托
     */
    private String orderType;
    /**
     * 委托价格
     */
    private BigDecimal price;
    /**
     * 成交均价（如果成交数量为0，该字段也为0）
     */
    private BigDecimal priceAvg;
    /**
     * 订单方向(buy/sell)
     */
    private OrderSide side;
    /**
     * 委托数量
     */
    private BigDecimal size;
    /**
     * 	订单状态( "-1":撤单成功, "0":等待成交 , "1":部分成交, "2":完全成交, "3":下单中, "4":撤单中, "5": 修改中）
     */
    private OkexOptionOrderStatus state;
    /**
     * 	状态变更时间，ISO 8601格式
     */
    private LocalDateTime timestamp;
    /**
     * 	1.买入，2.卖出，11.强平卖出，12.强平买入，13.减仓卖出，14.减仓买入
     */
    private OkexOptionOrderType type;
    /**
     * 标的指数，如BTC-USD
     */
    private CoinPair underlying;

}
