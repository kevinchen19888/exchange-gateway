package com.kevin.gateway.huobiapi.spot.vo;

import lombok.Data;

/**
 * 点卡划转
 */
@Data
public class SpotPointTransferVo {
    private String transactId;    //划转交易ID
    private long transactTime;//划转交易时间（unix time in millisecond）
}
