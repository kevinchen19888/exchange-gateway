package com.alchemy.gateway.exchangeclients.okex.resultModel;

import lombok.Data;

@Data
public class OrderAlgoInfo {

    public OrderAlgoInfo() {
        this.algo_price = "0";
        this.size = "0";
        this.trigger_price = "0";
    }

    /**
     * 币对信息
     */
    private String instrument_id;
    /**
     * 1：止盈止损
     * 2：跟踪委托
     * 3：冰山委托
     * 4：时间加权
     */
    private String order_type;
    /**
     * 委托时间
     */
    private String timestamp;

    /**
     * 委托失效时间
     */
    private String rejected_at;

    /**
     * 委托单id
     */
    private String algo_id;
    /**
     * 1: 待生效
     * 2: 已生效
     * 3: 已撤销
     * 4: 部分生效
     * 5: 暂停生效
     */
    private String status;
    /**
     * 委托数量
     */
    private String size;
    /**
     * 策略委托价格
     */
    private String algo_price;
    /**
     * 1：币币
     * 2：杠杆
     */
    private String mode;
    /**
     * 订单 ID
     */
    private String order_id;
    /**
     * 订单买卖类型 buy/sell
     */
    private String side;
    /**
     * 策略委托触发价格
     */
    private String trigger_price;
    /**
     * 1:限价 2:市场价；
     */
    private String algo_type;
}
