package com.alchemy.gateway.core.order;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * describe:
 *
 * @author zoulingwei
 */
@Data
public class TradesResult {

    List<TradeVo> tradeVos;
    /**
     * 返佣费用币种（没有返佣时为 NULL）
     */
    private String rebateCoin;
    /**
     * 返佣金额
     */
    private BigDecimal rebate;
}
