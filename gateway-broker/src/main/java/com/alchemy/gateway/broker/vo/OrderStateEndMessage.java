package com.alchemy.gateway.broker.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * describe:
 *
 * @author zoulingwei
 */
@Builder
@Data
public class OrderStateEndMessage {
    @JsonProperty("order_id")
    private Long orderId;//订单id
    @JsonProperty("finished_amount")
    private String finishedAmount;//已完成金额
    @JsonProperty("finished_volume")
    private String finishedVolume;//已完成成交量
    @JsonProperty("rebate")
    private String rebate;//返佣数量
    @JsonProperty("rebate_coin")
    private String rebateCoin;//返佣币种
    @JsonProperty("status")
    private String status;//状态
    @JsonProperty("fee")
    private String fee;//手续费用
}
