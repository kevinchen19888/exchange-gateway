package com.kevin.gateway.huobiapi.spot.request.marginLoanC2C;

import lombok.Data;

/**
 * 借入借出撤单
 */
@Data
public class SpotC2cCancelRequest {
    private String offerId;//订单ID
}
