package com.alchemy.gateway.exchangeclients.okex.resultModel;

import lombok.Data;

@Data
public class Fills {

    public Fills() {
        this.fee = "0";
        this.price = "0";
        this.size = "0";
    }

    /**
     * 账单 id
     */
    private Long ledger_id;
    /**
     * 成交id
     */
    private String trade_id;
    /**
     * 币种
     */
    private String currency;
    /**
     * 币种名称
     */
    private String instrument_id;
    /**
     * 成交价格
     */
    private String price;
    /**
     * 成交数量
     */
    private String size;
    /**
     * 订单 id
     */
    private Long order_id;
    /**
     * 订单成交时间
     */
    private String timestamp;
    /**
     * 流动方向
     */
    private String exec_type;
    /**
     * 手续费
     */
    private String fee;
    /**
     * 账单方向 buy、sell
     */
    private String side;
}
