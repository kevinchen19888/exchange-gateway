package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.type.CancelOnFailType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 修改订单
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AmendOrderRequest {

    /**
     * 0 ：不自动撤单 1:自动撤单 当订单修改失败时，该订单是否需要自动撤销。默认为0
     */
    private CancelOnFailType cancelOnFail;


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
     * new_size和new_price至少要传入一个。请求修改的新数量，对于部分成交订单，该数量应包含已成交数量。
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal newSize;

    /**
     * new_size和 new_price至少要传入一个。请求修改的新价格
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal newPrice;

}

