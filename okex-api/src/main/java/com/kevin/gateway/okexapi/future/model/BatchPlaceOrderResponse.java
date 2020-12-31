package com.kevin.gateway.okexapi.future.model;

import lombok.Data;

import java.util.List;

@Data
public class BatchPlaceOrderResponse {

    /**
     * order id
     */
    private boolean result;

    /**
     * 由您设置的订单ID来识别您的订单,格式是字母（区分大小写）+数字 或者 纯字母（区分大小写），1-32位字符 （不能重复）
     */
    private List<PlaceOrderResponse> orderInfo;


}

