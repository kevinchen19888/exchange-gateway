package com.kevin.gateway.okexapi.margin.response;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.base.util.AccountType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账单流水响应实体
 */
@Data
public class MarginBillsDetailsResponse {

    /**
     * 账单ID
     */
    private String ledgerId;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 币种
     */
    private Coin currency;

    /**
     * 变动数量
     */
    private BigDecimal amount;

    private String type;//流水来源

    /**
     * 账单创建时间
     */
    private LocalDateTime timestamp;

    /**
     * 多余字段
     */
    private LocalDateTime createdAt;

    private BillsDetailsTradeResponse details;

    @Data
    public static class BillsDetailsTradeResponse {
        /**
         * 交易的ID
         */
        private String orderId;

        /**
         * 交易的币对
         */
        private CoinPair instrumentId;

        /**
         * 多余字段
         */
        private String productId;

        /**
         * 转出账户
         * 1:币币账户
         * 3:交割合约
         * 4:法币账户
         * 5:币币杠杆账户
         * 6:资金账户
         * 8:余币宝账户
         * 9:永续合约账户
         * 12:期权合约
         * 14:挖矿账户
         * 17:借贷账户
         */
        private AccountType from;

        /**
         * 转入账户
         * 1:币币账户
         * 3:交割合约
         * 4:法币账户
         * 5:币币杠杆账户
         * 6:资金账户
         * 8:余币宝账户
         * 9:永续合约账户
         * 12:期权合约
         * 14:挖矿账户
         * 17:借贷账户
         */
        private AccountType to;
    }

}
