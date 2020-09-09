package com.alchemy.gateway.exchangeclients.okex.impl;

import com.alchemy.gateway.core.common.OrderLimit;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class OkexOrderLimit extends OrderLimit {
    //private BigDecimal minSize; //最小交易数量
    private BigDecimal sizeIncrement;//交易货币数量精度
    private BigDecimal tickSize;//交易价格精度
}
