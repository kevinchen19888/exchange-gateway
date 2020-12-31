package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.kevin.gateway.okexapi.option.util.OptionOrderType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Data
@Builder
public class OptionOrderRequest {
    /**
     * 由您设置的订单ID来识别您的订单,格式是字母（区分大小写）+数字 或者 纯字母（区分大小写），1-32位字符 （不能重复）
     */
    @Nullable
    private String clientOid;
    /**
     * 合约ID，如BTC-USD-190927-5000-C
     */
    private OptionMarketId instrumentId;
    /**
     * 订单方向(buy/sell)
     */
    private OrderSide side;
    /**
     * 参数填数字，0：普通委托（默认值）
     */
    @Nullable
    private OptionOrderType orderType;
    /**
     * 委托价格
     */
    private BigDecimal price;
    /**
     * 委托数量（合约张数）
     */
    private BigDecimal size;
    /**
     * 是否以对手价下单 0:不是（默认值） 1:是
     */
    @Nullable
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private boolean matchPrice;
}
