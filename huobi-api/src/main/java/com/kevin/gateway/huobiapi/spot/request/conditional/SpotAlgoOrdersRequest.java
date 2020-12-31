package com.kevin.gateway.huobiapi.spot.request.conditional;

import com.kevin.gateway.huobiapi.base.util.OrderSide;
import com.kevin.gateway.huobiapi.spot.SpotMarketId;
import com.kevin.gateway.huobiapi.spot.model.SpotTimeInForce;
import com.kevin.gateway.huobiapi.spot.model.SpotType;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 策略委托下单
 */
@Data
public class SpotAlgoOrdersRequest {
    private String accountId;//账户编号	当前仅支持spot账户ID、margin账户ID、super-margin账户ID，暂不支持c2c-margin账户ID
    private SpotMarketId symbol;    //	交易代码
    private BigDecimal orderPrice;    //	订单价格（对市价单无效）
    private OrderSide orderSide;//	订单方向	buy,sell
    private BigDecimal orderSize;//	订单数量（对市价买单无效）
    private BigDecimal orderValue;    //订单金额（仅对市价买单有效）
    /**
     * gtc(对orderType=market无效),
     * boc(对orderType=market无效),
     * ioc,fok(对orderType=market无效)
     */
    private SpotTimeInForce timeInForce;//订单有效期
    private SpotType orderType;//订单类型	limit,market
    private String clientOrderId;//	用户自编订单号（最长64位）
    private BigDecimal stopPrice;//	触发价
    private BigDecimal trailingRate;//	回调幅度（仅对追踪委托有效）	[0.001,0.050]
}
