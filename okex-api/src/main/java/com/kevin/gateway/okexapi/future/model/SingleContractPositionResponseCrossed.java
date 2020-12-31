package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.type.MarginMode;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 单个合约持仓信息  全仓 crossed参数
 */
@Data
public class SingleContractPositionResponseCrossed extends SingleContractPositionResponseBase {

    private MarginMode marginMode = MarginMode.crossed;
    /**
     * 预估强平价
     */
    private BigDecimal liquidationPrice;

    /**
     * 杠杆倍数
     */
    private int leverage;

}
