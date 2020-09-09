package com.alchemy.gateway.core.wallet;

import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.common.Market;

import java.util.List;

public interface AccountApi {
    /**
     * 获取账户下所有资产
     *
     * @param credentials 认证信息
     * @return 账户资产列表
     */
    List<AccountAssetResp> findUserAsset(Credentials credentials);

    /**
     * 查询用户账户状态是否正常
     *
     * @param credentials 认证信息
     * @return 账户状态, true:正常,false:异常
     */
    Boolean findUserStatus(Credentials credentials);

    /**
     * 通过游标获取用户账户资产转移记录
     * @param credentials 认证信息
     * @param cursorVo 资产游标
     * @return 资产转移记录
     */
    AssetTransferResult findAssetTransfers(Credentials credentials, CursorVo cursorVo, List<Market> markets);

    /**
     * 通过游标获取用户充提记录
     * @param credentials 认证信息
     * @param cursorVo 资产游标
     * @return 充提记录
     */
    DepositWithdrawResult findDepositWithdraws(Credentials credentials,CursorVo cursorVo);

}
