package com.alchemy.gateway.exchangeclients.okex.resultModel;

import lombok.Data;

@Data
public class OrderInfo {

    public OrderInfo() {
        this.fee = "0";
        this.size = "0";
        this.filled_notional = "0";
        this.filled_size = "0";
        this.rebate = "0";
        this.price = "0";
        this.notional = "0";
    }

    /**
     * 订单id
     */
    private String order_id;
    private String client_oid;
    /**
     * limit 订单类型的价格信息
     */
    private String price;
    /**
     * 委托数量
     */
    private String size;
    /**
     * 0：普通委托
     * 1：只做Maker（Post only）
     * 2：全部成交或立即取消（FOK）
     * 3：立即成交并取消剩余（IOC）
     */
    private String order_type;
    /**
     * market 订单类型的价格信息
     */
    private String notional;
    /**
     * 币对信息
     */
    private String instrument_id;
    /**
     * 订单买卖类型 buy/sell
     */
    private String side;
    /**
     * 订单类型 limit/market
     */
    private String type;
    /**
     * 委托时间
     */
    private String timestamp;
    /**
     * 成交数量
     */
    private String filled_size;
    /**
     * 计价成交量
     */
    private String filled_notional;
    /**
     * -2:失败
     * -1:撤单成功
     * 0:等待成交
     * 1:部分成交
     * 2:完全成交
     * 3:下单中
     * 4:撤单中
     */
    private String state;
    /**
     * 平均成交价
     */
    private String price_avg;
    /**
     * 交易手续费币种，如果是买的话，就是收取的BTC；如果是卖的话，收取的就是USDT
     */
    private String fee_currency;
    /**
     * 订单交易手续费。平台向用户收取的交易手续费，为负数，例如：-0.01
     */
    private String fee;
    /**
     * 反佣金币种 USDT
     */
    private String rebate_currency;
    /**
     * 反佣金额，平台向达到指定lv交易等级的用户支付的挂单奖励（返佣），如果没有返佣金，该字段为“”，为正数，例如：0.5
     */
    private String rebate;
}
