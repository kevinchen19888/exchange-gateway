package com.kevin.gateway.okexapi.swap.model;

import com.kevin.gateway.okexapi.future.model.ResultCode;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import lombok.Data;

import java.util.List;

@Data
public class BatchCancelSwapOrderResponse extends ResultCode {

    /**
     * order_id和client_oid不能同时为空。订单ID。
     */
    private List<String> ids;

    /**
     * order_id和client_oid不能同时为空。在下单时由您设置的ID来识别您的订单, 类型为字母（大小写）+数字或者纯字母（大小写），1-32位字符。
     */
    private List<String> clientOids;

    /**
     * 合约ID
     */
    private SwapMarketId instrumentId;


}

