package com.kevin.gateway.okexapi.fundingaccount.domain;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.base.util.AccountType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

/**
 * 资金划转请求
 */
@Data
@Builder
public class AssetTransferRequest {
    /**
     * 币种，如usdt
     */
    private Coin currency;
    /**
     * 划转数量
     */
    private BigDecimal amount;
    /**
     * 0:母/子账户中各个账户之间划转;1:母账户转子账号;2:子账户转母账号 默认为0
     */
    @Nullable
    private TransferType type = TransferType.DEFAULT;
    /**
     * 转出账户/1:币币账户;3:交割合约;4:法币账户;5:币币杠杆账户;6:资金账户;8:余币宝账户;9:永续合约账户;12:期权合约;14:挖矿账户;17:借贷账户
     */
    private AccountType from;
    /**
     * 转入账户/1:币币账户;3:交割合约;4:法币账户;5:币币杠杆账户;6:资金账户;8:余币宝账户;9:永续合约账户;12:期权合约;14:挖矿账户;17:借贷账户
     */
    private AccountType to;

    /**
     * 子账号登录名，type为1、2、时，sub_account为必填项
     */
    @Nullable
    private String subAccount;
    /**
     * 杠杆转出币对 或者USDT保证金合约转出的underlying，如：btc-usdt，仅限已开通杠杆币对或者合约的underlying。
     */
    @Nullable
    private CoinPair instrumentId;
    /**
     * 杠杆转入币对 或者USDT保证金合约转入的underlying，如：btc-usdt，仅限已开通杠杆币对或者合约的underlying。
     */
    @Nullable
    private CoinPair toInstrumentId;

    public enum TransferType {
        /**
         * 默认值
         */
        DEFAULT(0),
        /**
         * 母账户转子账号
         */
        TO_SUB(1),
        /**
         * 子账户转母账号
         */
        TO_MAIN(2);

        private final int val;

        TransferType(int val) {
            this.val = val;
        }

        @JsonValue
        public int getVal() {
            return val;
        }

        @JsonCreator
        public static TransferType fromVal(int value) {
            for (TransferType type : TransferType.values()) {
                if (type.val == value) {
                    return type;
                }
            }
            throw new IllegalArgumentException("无效的资金划转类型,s:" + value);
        }
    }


}
