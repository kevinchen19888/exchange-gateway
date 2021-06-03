package com.kevin.gateway.huobiapi.spot.response.marginloanc2c;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.model.OrderState;
import com.kevin.gateway.huobiapi.spot.model.SpotC2cSide;
import com.kevin.gateway.huobiapi.spot.model.SpotLoanTerm;
import com.kevin.gateway.huobiapi.spot.model.SpotTimeInForce;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查询所有借入借出订单
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotSearchC2cOfferResponse extends SpotBaseResponse {
    private List<SpotSearchC2cOffer> data;

    @Data
    private static class SpotSearchC2cOffer {
        private String offerId;//订单ID
        private long createTime;//订单创建时间（unix time in millisecond）
        private long lastActTime;//订单更新时间（unix time in millisecond）
        private OrderState offerStatus;//订单状态（有效值：submitted, filled, partial-filled, canceled, partial-canceled）
        private String accountId;//账户ID
        private SpotCoin currency;//借入/借出币种
        private SpotC2cSide side;//订单方向（有效值：lend, borrow）
        private SpotTimeInForce timeInForce;//订单有效期（gtc, ioc）
        private BigDecimal origAmount;//订单原始金额
        private BigDecimal amount;//订单剩余金额
        private BigDecimal interestRate;//日息率
        private SpotLoanTerm loanTerm;//借币期限
    }
}
