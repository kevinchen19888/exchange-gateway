package com.kevin.gateway.okexapi.fundingaccount.domain;

import com.kevin.gateway.core.Coin;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 提币记录Vo
 */
@Data
public class WithdrawalHistoryResponse {
    /**
     * 币种
     */
    private Coin currency;
    /**
     * 数量
     */
    private BigDecimal amount;
    /**
     * 提币申请时间
     */
    private LocalDateTime timestamp;
    /**
     * 提币地址（如果收币地址是OKEx平台地址，则此处将显示用户账户）
     */
    private String from;
    /**
     * 收币地址
     */
    private String to;
    /**
     * 部分币种提币需要标签，若不需要则不返回此字段
     */
    private String tag;
    /**
     * 部分币种提币需要此字段，若不需要则不返回此字段
     */
    private String paymentId;
    /**
     * 部分币种提币需要此字段，若不需要则不返回此字段
     */
    private String memo;
    /**
     * 提币哈希记录（内部转账将不返回此字段）
     */
    private String txid;
    /**
     * 提币手续费,eg:0.01000000eth
     */
    private String fee;
    /**
     * 提现状态/-3:撤销中-2:已撤销-1:失败0:等待提现1:提现中,2:已汇出,3:邮箱确认4:人工审核中5:等待身份认证
     */
    private Status status;
    /**
     * 提币申请ID
     */
    private String withdrawalId;

    public enum Status {
        /**
         * -3:撤销中
         */
        PENDING_CANCEL(-3),
        /**
         * -2:已撤销
         */
        CANCELED(-2),
        /**
         * -1:失败
         */
        FAILED(-1),
        /**
         * 0:等待提现
         */
        PENDING(0),
        /**
         * 1:提现中,
         */
        SENDING(1),
        /**
         * 2:已汇出,
         */
        SENT(2),
        /**
         * 3:邮箱确认
         */
        ON_EMAIL_VERIFICATION(3),
        /**
         * 4:人工审核中
         */
        ON_MANUAL_CHECK(4),
        /**
         * 5:等待身份认证
         */
        PENDING_IDENTITY_VERIFICATION(5);

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
            throw new IllegalArgumentException("无效的提币状态");
        }
    }

}
