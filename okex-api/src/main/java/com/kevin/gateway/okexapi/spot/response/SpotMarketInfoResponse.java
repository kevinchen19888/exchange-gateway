package com.kevin.gateway.okexapi.spot.response;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.okexapi.spot.SpotMarketId;
import com.kevin.gateway.okexapi.spot.model.SpotAccountTradeFeeCategory;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 获取币对信息
 */
@Data
public class SpotMarketInfoResponse {
    /**
     * 币对名称
     */
    private SpotMarketId instrumentId;  // TODO: 使用 SpotMarketId
    /**
     * 交易货币币种
     */
    private Coin baseCurrency;
    /**
     * 计价货币币种
     */
    private Coin quoteCurrency;
    /**
     * 最小交易数量
     */
    private BigDecimal minSize;
    /**
     * 交易货币数量精度
     */
    private BigDecimal sizeIncrement;
    /**
     * 交易价格精度
     */
    private BigDecimal tickSize;

    /**
     * 手续费档位，1：第一档，2：第二档， 3：第三档
     */
    private SpotAccountTradeFeeCategory category;
}
