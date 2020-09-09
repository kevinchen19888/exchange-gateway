package com.alchemy.gateway.exchangeclients.bitmex;

import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.wallet.*;

import java.util.List;

/**
 * @author kevin chen
 */
public class BitmexAccountApi implements AccountApi {

    @Override
    public List<AccountAssetResp> findUserAsset(Credentials credentials) {
        return null;
    }

    @Override
    public Boolean findUserStatus(Credentials credentials) {
        return null;
    }

    @Override
    public AssetTransferResult findAssetTransfers(Credentials credentials, CursorVo cursorVo, List<Market> markets) {
        return null;
    }

    @Override
    public DepositWithdrawResult findDepositWithdraws(Credentials credentials, CursorVo cursorVo) {
        return null;
    }

}
