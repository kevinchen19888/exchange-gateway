package com.kevin.gateway.huobiapi.spot.response.account;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.SpotTransactType;
import com.kevin.gateway.huobiapi.spot.model.SpotTransferType;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 财务流水
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotAccountLedgerResponse extends SpotBaseResponse {
    private List<SpotAccountLedgerInfo> data;

    @Data
    private static class SpotAccountLedgerInfo {
        private Integer accountId;    //	账户编号
        private SpotCoin currency;    //	币种
        private BigDecimal transactAmt;//	变动金额（入账为正 or 出账为负）
        private SpotTransactType transactType;    //	变动类型	transfer（划转）
        private SpotTransferType transferType;//划转类型（仅对transactType=transfer有效）
        private Integer transactId;    //	交易流水号
        private long transactTime;    //	交易时间
        private Integer transferer;    //	付款方账户ID
        private Integer transferee;    //收款方账户ID

    }
}
