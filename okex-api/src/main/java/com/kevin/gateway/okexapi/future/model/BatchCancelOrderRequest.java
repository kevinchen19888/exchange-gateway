package com.kevin.gateway.okexapi.future.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchCancelOrderRequest {

    /**
     * 订单 id 列表
     */
    private List<String> orderIds;

    /**
     * order_id和client_oid不能同时为空。在下单时由您设置的ID来识别您的订单, 类型为字母（大小写）+数字或者纯字母（大小写），1-32位字符。
     */
    private List<String> clientOids;
}
