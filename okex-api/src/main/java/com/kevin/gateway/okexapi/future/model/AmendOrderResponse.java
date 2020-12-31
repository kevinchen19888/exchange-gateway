package com.kevin.gateway.okexapi.future.model;

import lombok.Data;


@Data
public class AmendOrderResponse extends ResultCode {

    /**
     * order_id和client_oid不能同时为空。订单ID。
     */
    private String orderId;

    /**
     * order_id和client_oid不能同时为空。在下单时由您设置的ID来识别您的订单, 类型为字母（大小写）+数字或者纯字母（大小写），1-32位字符。
     */
    private String clientOid;

    /**
     * 客户可选择提供request_id，如果提供，在修改返回状态中，会包含相应的request_id，方便客户找到相应的修改请求。类型为字母（大小写）+数字或者纯字母（大小写），1-32位字符。
     */
    private String requestId;

    /**
     * 提示消息
     */
    private String message;


}

