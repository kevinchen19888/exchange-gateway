package com.kevin.gateway.huobiapi.spot.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kevin.gateway.huobiapi.spot.model.SpotOrdersPlaceType;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OrderRequest {

    /**
     * 账户id:可以在登录web端后进入调试界面(按F12)通过点击:'订单'菜单下的任意一个子菜单(如币币&杠杆订单)
     * 获取返回后的响应结果,然后通过调试工具中的 search工具搜索关键字:account-id 获取;
     */
    @JsonProperty("account-id")
    private String accountId;
    /**
     * 要购买的打新币对,如要购买RAI,则值应为:raiusdt
     */
    private String symbol;
    /**
     * 订单交易量（市价买单为订单交易额）
     * 限价单为
     */
    private BigDecimal amount;
    /**
     * 订单价格（对市价单无效）
     */
    private BigDecimal price;
    private SpotOrdersPlaceType type;
}
