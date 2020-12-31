package com.kevin.gateway.huobiapi.spot.response.account;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.kevin.gateway.huobiapi.spot.vo.SpotAccountAssetValuationVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 获取账户资产估值
 */
@ToString
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotAccountAssetValuationResponse extends SpotBaseResponse {
    private SpotAccountAssetValuationVo data;

}
