package com.alchemy.gateway.exchangeclients.okex.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PlaceOrderParam {
    /**
     * 客户端下单 标示id 非必填
     */
    @JsonProperty(value = "instrument_id")
    private String instrumentId;

    @JsonProperty(value = "client_oid")
    private String clientOid;

    @JsonProperty(value = "order_id")
    private String orderId;

    /**
     * 买卖类型 buy/sell
     */
    private String side;
    /**
     * 订单类型 限价单 limit 市价单 market
     */
    private String type;
    /**
     * 交易数量
     */
    private String size;
    /**
     * 限价单使用 价格
     */
    private String price;
    /**
     * 市价单使用 价格
     */
    private String notional;

    @JsonProperty(value = "order_type")
    private String orderType;
    /**
     * 1币币交易 2杠杆交易
     */
    @JsonProperty(value = "margin_trading")
    private String marginTrading;

}
