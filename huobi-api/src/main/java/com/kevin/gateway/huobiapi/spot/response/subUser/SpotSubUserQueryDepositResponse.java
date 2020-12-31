package com.kevin.gateway.huobiapi.spot.response.subUser;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.SpotDepositState;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 子用户充币记录查询
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotSubUserQueryDepositResponse extends SpotBaseResponse {
    private List<SpotSubUserQueryDeposit> data;

    @Data
    private static class SpotSubUserQueryDeposit {
        private String id;//充币订单id
        private SpotCoin currency;//币种
        private String txHash;//交易哈希
        private String chain;//链名称
        private BigDecimal amount;//个数
        private String address;//地址
        private String addressTag;//地址标签
        private SpotDepositState state;//状态
        private long createTime;//发起时间
        private long updateTime; //最后更新时间
    }
}
