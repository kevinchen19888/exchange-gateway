package com.kevin.gateway.okexapi.spot.websocket.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 *
 */
@Data
public class CurrencyData {
    /**
     * 可用于交易或资金划转的数量
     */
    private BigDecimal available;
    /**
     * 余额
     */
    private BigDecimal balance;
    /**
     * 已借币（已借未还的部分）
     */
    private BigDecimal borrowed;
    /**
     * 	冻结（不可用）
     */
    private BigDecimal hold;
    /**
     * 利息（未还的利息）
     */
    private BigDecimal lendingFee;

}