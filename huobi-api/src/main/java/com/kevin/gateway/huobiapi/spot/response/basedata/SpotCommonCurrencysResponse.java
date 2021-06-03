package com.kevin.gateway.huobiapi.spot.response.basedata;

import com.kevin.gateway.huobiapi.spot.SpotCoin;
import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * 获取所有币种
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotCommonCurrencysResponse extends SpotBaseResponse {
    private List<SpotCoin> data;
}
