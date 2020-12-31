package com.kevin.gateway.huobiapi.spot.response.marginLoanC2C;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 借入借出下单
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotC2cOfferResponse extends SpotBaseResponse {
    private SpotC2cOffer data;

    @Data
    private static class SpotC2cOffer {
        private String offerId;    //订单ID
        private long createTime;//订单创建时间（unix time in millisecond）
    }
}
