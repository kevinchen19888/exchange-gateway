package com.kevin.gateway.huobiapi.spot.response.account;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import com.kevin.gateway.huobiapi.spot.vo.SpotPointTransferVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 点卡划转
 */
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class SpotPointTransferResponse extends SpotBaseResponse {
    private SpotPointTransferVo data;

}
