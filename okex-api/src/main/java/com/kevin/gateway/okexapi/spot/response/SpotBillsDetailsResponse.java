package com.kevin.gateway.okexapi.spot.response;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.okexapi.spot.model.SpotBillsTransferFromOrTo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 账单流水响应实体
 */
@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SpotBillsDetailsResponse.SpotBillsDetailsRebateResponse.class, name = "rebate"),
        @JsonSubTypes.Type(value = SpotBillsDetailsResponse.SpotBillsDetailsTradeResponse.class, name = "trade"),
        @JsonSubTypes.Type(value = SpotBillsDetailsResponse.SpotBillsDetailsTransferResponse.class, name = "transfer")
})
public class SpotBillsDetailsResponse {

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

    //private String type;//流水来源

    /**
     * 账单创建时间
     */
    private LocalDateTime timestamp;

    /**
     * 多余字段
     */
    private LocalDateTime createdAt;

    //如果type是trade，则会有该details字段将包含order，instrument信息,
    //如果type是transfer，则会有该details字段将包含from，to信息
    //private String details;

    /**
     * 交易产生的资金变动
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    public static class SpotBillsDetailsTradeResponse extends SpotBillsDetailsResponse {

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
        }
    }

    /**
     * 资金转入/转出
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    public static class SpotBillsDetailsTransferResponse extends SpotBillsDetailsResponse {

        private BillsDetailsTransferResponse details;

        @Data
        public static class BillsDetailsTransferResponse {
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
            private SpotBillsTransferFromOrTo from;

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
            private SpotBillsTransferFromOrTo to;
        }
    }

    /**
     * 返佣
     */
    @EqualsAndHashCode(callSuper = true)
    @ToString(callSuper = true)
    @Data
    public static class SpotBillsDetailsRebateResponse extends SpotBillsDetailsResponse {
        private String details;
    }
}
