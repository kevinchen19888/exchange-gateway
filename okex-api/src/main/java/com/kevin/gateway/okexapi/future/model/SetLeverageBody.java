package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.type.DirectionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;


/**
 * 设定合约币种杠杆倍数
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SetLeverageBody {

    /**
     * 全仓/逐仓 参数  要设定的杠杆倍数，填写1-100的数值
     */

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private int leverage;


    /**
     * 逐仓 参数  :    合约ID，如BTC-USD-180213 ,BTC-USDT-191227
     */
    private FutureMarketId instrumentId;


    /**
     * 逐仓 参数  : 开仓方向  long（做多）或short（做空）
     */
    private DirectionType direction;

}

