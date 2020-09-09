package com.alchemy.gateway.exchangeclients.okex.resultModel;

import lombok.Data;

@Data
public class OrderAlgoResult {
    /**
     * 调用接口返回结果
     */
    private String result;
    /**
     * 订单ID，下单失败时，此字段值为-1
     */
    private String algo_id;
    /**
     * 错误码，下单成功时为0，下单失败时会显示相应错误码
     */
    private String error_code;
    /**
     * 错误信息，下单成功时为空，下单失败时会显示错误信息
     */
    private String error_message;

}
