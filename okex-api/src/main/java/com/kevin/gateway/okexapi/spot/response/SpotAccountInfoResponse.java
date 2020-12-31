package com.kevin.gateway.okexapi.spot.response;

import com.kevin.gateway.core.Coin;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 币币账户信息/单一币种账户信息响应实体
 */
@Data
public class SpotAccountInfoResponse {

    /**
     * 账户id
     */
    private String id;

    /**
     * 币种
     */
    private Coin currency;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 可用于交易的数量
     */
    private BigDecimal available;

    /**
     * 冻结(不可用)
     */
    private BigDecimal hold;

    /**
     * 文档未标明,多余参数
     */
    private BigDecimal frozen;

    /**
     * 文档未标明,多余参数
     */
    private BigDecimal holds;
}
