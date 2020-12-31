package com.kevin.gateway.huobiapi.spot.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SpotPointTransferRequest {
    private String fromUid;    //	转出方UID
    private String toUid;    //	转入方UID
    private String groupId;    //	点卡分组ID
    private BigDecimal amount;    //	划转数量（最高精度: 8位小数）
}
