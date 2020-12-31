package com.kevin.gateway.huobiapi.spot.response.wallet;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.SpotDepositState;
import com.kevin.gateway.huobiapi.spot.model.SpotWithdrawState;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 充提币记录
 */
@Data
public class SpotDepositWithdrawResponse {
    private List<SpotDepositWithdraw> data;

    @Data
    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type"
    )
    @JsonSubTypes({
            @JsonSubTypes.Type(value = SpotDepositWithdraw.SpotDeposit.class, name = "deposit"),
            @JsonSubTypes.Type(value = SpotDepositWithdraw.SpotWithdraw.class, name = "withdraw")
    })
    private static class SpotDepositWithdraw {
        private long id;//充币订单id
        private SpotCoin currency;//	币种
        @JsonProperty(value = "tx-hash")
        private String txHash;    //	交易哈希
        private String chain;//	链名称
        private BigDecimal amount;//	个数
        private String address;    //	目的地址
        @JsonProperty(value = "address-tag")
        private String addressTag;//	地址标签
        private BigDecimal fee;//手续费
        @JsonProperty(value = "created-at")
        private long createdAt;//	发起时间
        @JsonProperty(value = "updated-at")
        private long updatedAt;    //	最后更新时间

        /**
         * 充币状态
         */
        @EqualsAndHashCode(callSuper = true)
        @ToString(callSuper = true)
        @Data
        public static class SpotDeposit extends SpotDepositWithdraw {
            private SpotDepositState state;    //充币状态
        }

        /**
         * 提币状态
         */
        @EqualsAndHashCode(callSuper = true)
        @ToString(callSuper = true)
        @Data
        public static class SpotWithdraw extends SpotDepositWithdraw {
            private SpotWithdrawState state;    //提币状态
            @JsonProperty(value = "error-code")
            private String errorCode;//提币失败错误码，仅type为”withdraw“，且state为”reject“、”wallet-reject“和”failed“时有。
            @JsonProperty(value = "error-msg")
            private String errorMsg;    //	提币失败错误描述，仅type为”withdraw“，且state为”reject“、”wallet-reject“和”failed“时有。
        }
    }
}
