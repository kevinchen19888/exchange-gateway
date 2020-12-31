package com.kevin.gateway.okexapi.spot.request;

import com.kevin.gateway.core.CoinPair;
import lombok.Data;

import java.util.List;

/**
 * 批量撤销订单
 */
@Data
public class SpotCancelMultipleOrdersRequest {
    /**
     * order_id和client_oid必须且只能选一个填写，订单ID
     */
    private List<String> orderIds;

    /**
     * order_id和client_oid必须且只能选一个填写，由您设置的订单ID来识别您的订单，类型为字母（大小写）+数字或者纯字母（大小写），1-32位字符
     */
    private List<String> clientOids;

    /**
     * 币种
     */
    private CoinPair instrumentId;
}
