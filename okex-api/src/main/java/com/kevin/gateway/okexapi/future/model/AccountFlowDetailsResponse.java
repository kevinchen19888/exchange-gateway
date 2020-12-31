package com.kevin.gateway.okexapi.future.model;

import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.future.type.AccoutFromToType;
import lombok.Data;

@Data
public class AccountFlowDetailsResponse {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 合约ID
     */
    private FutureMarketId instrumentId;

    /**
     * 转出账户
     * 1:币币账户
     * 3:交割合约
     * 4:法币账户
     * 5:币币杠杆账户
     * 6:资金账户
     * 8:余币宝账户
     * 9:永续合约账户
     * 12:期权合约
     * 14:挖矿账户
     * 17:借贷账户
     */
    private AccoutFromToType from;

    /**
     * 转入账户
     * 1:币币账户
     * 3:交割合约
     * 4:法币账户
     * 5:币币杠杆账户
     * 6:资金账户
     * 8:余币宝账户
     * 9:永续合约账户
     * 12:期权合约
     * 14:挖矿账户
     * 17:借贷账户
     */
    private AccoutFromToType to;
}
