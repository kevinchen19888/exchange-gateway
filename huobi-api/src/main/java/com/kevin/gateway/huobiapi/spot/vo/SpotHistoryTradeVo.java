package com.kevin.gateway.huobiapi.spot.vo;

import com.kevin.gateway.huobiapi.spot.response.SpotBaseResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class SpotHistoryTradeVo extends SpotBaseResponse {
    private List<SpotTradeVo> data;
}
