package com.kevin.gateway.okexapi.fundingaccount.domain;

import com.kevin.gateway.okexapi.base.util.AccountType;
import lombok.Data;

/**
 * 获取充值地址
 */
@Data
public class DepositAddressResponse {
    /**
     * 充值地址
     */
    private String address;
    /**
     * 部分币种充值需要标签，若不需要则不返回此字段
     */
    private String tag;
    /**
     * 部分币种充值需要标签，若不需要则不返回此字段
     */
    private String memo;
    /**
     * 部分币种充值需要此字段，若不需要则不返回此字段
     */
    private String paymentId;
    /**
     * 币种，如BTC
     */
    private String currency;// resp:usdt-erc20
    /**
     * 转入账户/1:币币3:交割合约4:C2C6:资金账户8:余币宝9:永续合约12:期权
     */
    private AccountType to;
}
