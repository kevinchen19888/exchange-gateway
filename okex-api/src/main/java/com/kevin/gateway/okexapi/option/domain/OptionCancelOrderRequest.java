package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.core.CoinPair;
import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class OptionCancelOrderRequest {

    /**
     * 标的指数，如BTC-USD
     */
    private CoinPair underlying;

    /**
     * order_id和client_oid必须且只能选一个填写。订单ID。
     */
    @Nullable
    private String orderId;

    /**
     * order_id和client_oid必须且只能选一个填写。在下单时由您设置的ID来识别您的订单,
     * 类型为字母（大小写）+数字或者纯字母（大小写） 1-32位字符
     */
    @Nullable
    private String clientOid;
}
