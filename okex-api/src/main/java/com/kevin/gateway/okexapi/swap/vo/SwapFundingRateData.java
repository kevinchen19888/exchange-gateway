package com.kevin.gateway.okexapi.swap.vo;

import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** 合约资金费率
 * {
 *     "table":"swap/funding_rate",
 *     "data":[
 *         {
 *             "estimated_rate":"0.00019",
 *             "funding_rate":"0.00022993",
 *             "funding_time":"2019-10-11T16:00:00.000Z",
 *             "instrument_id":"BTC-USD-SWAP",
 *             "interest_rate":"0",
 *             "settlement_time":"2019-10-12T08:00:00.000Z"
 *         }
 *     ]
 * }
 */
@Data
public class SwapFundingRateData {

    /**
     * 下一期预测资金费率
     */
    private BigDecimal estimatedRate;

    /**
     * 当期资金费率
     */
    private BigDecimal fundingRate;

    /**
     * 当期资金费率时间
     */
    private LocalDateTime fundingTime;

    /**
     * 合约名称，如BTC-USD-SWAP
     */
    private SwapMarketId instrumentId;

    /**
     * 利率
     */
    private BigDecimal interestRate;

    /**
     * 结算时间
     */
    private LocalDateTime settlementTime;

}
