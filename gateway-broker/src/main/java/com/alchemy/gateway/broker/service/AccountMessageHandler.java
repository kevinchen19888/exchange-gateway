package com.alchemy.gateway.broker.service;

import com.alchemy.gateway.core.wallet.AccountAssetResp;

import java.util.List;

/**
 * describe:
 *
 * @author zoulingwei
 */
public interface AccountMessageHandler {
    void createAccount(String exchangeName, String accountId, String accessKey, String secretKey);
    void updateAsset(List<AccountAssetResp> userAsset, Long id, String mianAccountId);
    void deleted(String accountId);

    void enabled(String accountId);

    void disabled(String accountId);

    void assetSynchronizing(String accountId);

    void errorResolved(String accountId);
}
