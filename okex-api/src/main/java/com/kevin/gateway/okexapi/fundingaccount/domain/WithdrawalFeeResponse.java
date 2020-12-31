package com.kevin.gateway.okexapi.fundingaccount.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 提币手续费Vo
 */
@Data
public class WithdrawalFeeResponse {
    /**
     * 币种
     * <p>
     * 注意: 这个接口中会返回类似这样的币种格式：USDT-ERC20 或者 USDT-TRC20
     */
    private String currency;

    /**
     * 最小提币手续费数量
     */
    private BigDecimal minFee;
    /**
     * 最大提币手续费数量
     */
    private BigDecimal maxFee;
}
