package com.kevin.gateway.okexapi.swap.common.model;


import com.kevin.gateway.okexapi.future.common.type.LiquidationType;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class SwapLiquidationOrderResponse {


    /**
     * 合约ID
     */
    private SwapMarketId instrumentId;


    /**
     * 数量
     */
    private BigDecimal size;

    /**
     * 强平单的委托时间
     */
    private LocalDateTime createdAt;

    /**
     * 穿仓用户亏损
     */
    private BigDecimal loss;

    /**
     * 订单价格
     */
    private BigDecimal price;

    /**
     * 订单类型
     * 3平多
     * 4:平空
     */
    private LiquidationType type;

}
