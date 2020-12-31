package com.kevin.gateway.okexapi.fundingaccount.domain;

import com.kevin.gateway.core.Coin;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 */
@Data
public class DepositHistoryResponse {

    /**
     * 币种名称，如：BTC
     */
    private Coin currency;

    /**
     * 充值数量
     */
    private BigDecimal amount;

    /**
     * 充值地址，只显示内部账户转账地址，不显示区块链充值地址
     */
    private String from;

    /**
     * 到账地址
     */
    private String to;

    /**
     * 区块转账哈希记录
     */
    private String txid;

    /**
     * 充值到账时间
     */
    private LocalDateTime timestamp;

    /**
     * 充值记录ID
     */
    private String depositId;

    /**
     * 充值状态/0:等待确认1:确认到账2:充值成功
     */
    private Status status;

    public enum Status {
        /**
         * 等待确认
         */
        WAITING_CONFIRM(0),
        /**
         * 确认到账
         */
        DEPOSIT_CREDITED(1),
        /**
         * 充值成功
         */
        DEPOSIT_SUCCESSFUL(2);

        private final int val;

        Status(int val) {
            this.val = val;
        }

        @JsonValue
        public int getVal() {
            return val;
        }

        @JsonCreator
        public static Status valueOf(int value) {
            for (Status status : Status.values()) {
                if (status.val == value) {
                    return status;
                }
            }
            throw new IllegalArgumentException("无效的充值状态,s:" + value);
        }
    }
}
