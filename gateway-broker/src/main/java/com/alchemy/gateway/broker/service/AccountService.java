package com.alchemy.gateway.broker.service;

import com.alchemy.gateway.broker.entity.Account;
import com.alchemy.gateway.broker.entity.AssetCursor;
import com.alchemy.gateway.broker.entity.type.AssetCursorType;
import com.alchemy.gateway.core.common.AccountInfo;
import com.alchemy.gateway.core.wallet.AccountAssetResp;

import java.util.List;

/**
 * 账户服务
 */
public interface AccountService {

    /**
     * 获取所有的启用账户
     *
     * @return 启用账户的列表
     */
    List<Account> findAllEnabledAccounts();

    /**
     * 获取账户信息
     *
     * @param id 账户id
     * @return 账户信息
     */
    AccountInfo getAccountInfo(Long id);


    /**
     * 根据AccountId查询用户
     *
     * @param accountId 主平台用户id
     * @return 账户信息
     */
    Account findByAccountId(String accountId);

    /**
     * 资产同步接口
     *
     * @param userAsset     最新用户资产信息
     * @param id            用户id
     * @param mianAccountId 主平台用户id
     */
    void updateAsset(List<AccountAssetResp> userAsset, Long id, String mianAccountId);

    /**
     * 获取游标
     * @param accountId 账户id
     * @param assetCursorType 游标类型
     * @return AssetCursor
     */
    AssetCursor getAssetCursor(Long accountId, AssetCursorType assetCursorType);
}
