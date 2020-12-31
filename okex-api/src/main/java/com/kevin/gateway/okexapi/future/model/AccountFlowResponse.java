package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.future.type.AccoutFlowSourceType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


/**
 * 账单流水查询
 */
@Data
public class AccountFlowResponse {

    /**
     * 账单ID
     */
    private String ledgerId;

    /**
     * 标的指数，如：btc-usd,btc-usdt
     */
    private CoinPair underlying;

    /**
     * 变动数量
     */
    private BigDecimal amount;

    /**
     * 流水来源
     */
    private AccoutFlowSourceType type;

    /**
     * 账单创建时间
     */
    private LocalDateTime timestamp;

    /**
     * 如果type是trade或者fee，则会有该details字段将包含order，instrument信息,如果type是transfer，则会有该details字段将包含from，to信息
     */
    private AccountFlowDetailsResponse details;


    /**
     * 开仓平仓的张数（开仓为正数，平仓为负数，当type是fee 和transfer时，balance为0）
     */
    private BigDecimal balance;

    /**
     * 账户余额币种
     */
    private CoinPair currency;

}

