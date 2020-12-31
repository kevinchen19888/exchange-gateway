package com.kevin.gateway.huobiapi.spot.vo;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 获取账户资产估值
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotAccountAssetValuationVo extends SpotBaseResponse {
    /**
     * 按照某一个法币为单位的总资产估值
     */
    private BigDecimal balance;
    /**
     * 数据返回时间，为unix time in millisecond
     */
    private long timestamp;
}
