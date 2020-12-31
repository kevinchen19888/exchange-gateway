package com.kevin.gateway.huobiapi.spot.response.account;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.SpotTransactType;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 账户流水
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotAccountHistoryResponse extends SpotBaseResponse {
    private List<SpotAccountHistoryInfo> data;

    @Data
    private static class SpotAccountHistoryInfo {

        @JsonProperty(value = "account-id")
        private long accountId;    //	账户编号
        private SpotCoin currency;    //	币种
        @JsonProperty(value = "transact-amt")
        private BigDecimal transactAmt;    //	变动金额（入账为正 or 出账为负）
        @JsonProperty(value = "transact-type")
        private SpotTransactType transactType;    //	变动类型
        @JsonProperty(value = "avail-balance")
        private BigDecimal availBalance;    //	可用余额
        @JsonProperty(value = "acct-balance")
        private BigDecimal acctBalance;    //	账户余额
        @JsonProperty(value = "transact-time")
        private long transactTime;    //	交易时间（数据库记录时间）
        @JsonProperty(value = "record-id")
        private long recordId;    //	数据库记录编号（全局唯一）
    }

    @JsonProperty(value = "next-id")
    private long nextId;    //	下页起始编号（仅在查询结果需要分页返回时包含此字段，见注2）
}
