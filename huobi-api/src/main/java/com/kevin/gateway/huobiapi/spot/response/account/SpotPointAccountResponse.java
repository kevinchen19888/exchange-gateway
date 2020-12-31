package com.kevin.gateway.huobiapi.spot.response.account;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.kevin.gateway.huobiapi.spot.vo.SpotPointAccountVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 点卡余额查询
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotPointAccountResponse extends SpotBaseResponse {
    private SpotPointAccountVo data;
}
