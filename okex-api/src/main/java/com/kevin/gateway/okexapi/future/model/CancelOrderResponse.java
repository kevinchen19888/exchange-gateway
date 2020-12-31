package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import lombok.Data;

@Data
public class CancelOrderResponse extends ResultCode {

    /**
     * order_id和client_oid不能同时为空。订单ID。
     */
    private String orderId;

    /**
     * order_id和client_oid不能同时为空。在下单时由您设置的ID来识别您的订单, 类型为字母（大小写）+数字或者纯字母（大小写），1-32位字符。
     */
    private String clientOid;

    /**
     * 合约ID
     */
    private FutureMarketId instrumentId;


}

