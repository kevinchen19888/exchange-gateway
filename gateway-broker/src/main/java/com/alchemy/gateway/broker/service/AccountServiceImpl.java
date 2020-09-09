package com.alchemy.gateway.broker.service;

import com.alchemy.gateway.broker.entity.*;
import com.alchemy.gateway.broker.entity.type.AccountStatusEnum;
import com.alchemy.gateway.broker.entity.type.AssetCursorType;
import com.alchemy.gateway.broker.exception.AccountNotExistExecution;
import com.alchemy.gateway.broker.exception.CredentialsNotExistException;
import com.alchemy.gateway.broker.repository.*;
import com.alchemy.gateway.broker.vo.AccountAssetCoinVo;
import com.alchemy.gateway.broker.vo.AccountAssetUpdateMessage;
import com.alchemy.gateway.core.ExchangeManager;
import com.alchemy.gateway.core.common.AccountInfo;
import com.alchemy.gateway.core.common.AccountInfoProvider;
import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.wallet.*;
import com.alchemy.gateway.market.MarketManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService, AccountInfoProvider, AccountMessageHandler {

    private final AccountRepository accountRepository;
    private final AccountAssetRepository accountAssetRepository;
    private final AssetTransferRepository assetTransferRepository;
    private final DepositWithdrawRepository depositWithdrawRepository;
    private final AssetCursorRepository assetCursorRepository;
    private final ExchangeManager exchangeManager;
    private final AccountMessageSender accountMessageSender;
    private final HashOperations<String, String, String> redisAccountHash;
    private final MarketManager marketManager;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository,
                              AccountAssetRepository accountAssetRepository,
                              AssetTransferRepository assetTransferRepository,
                              DepositWithdrawRepository depositWithdrawRepository,
                              AssetCursorRepository assetCursorRepository,
                              HashOperations<String, String, String> redisAccountHash,
                              ExchangeManager exchangeManager,
                              com.alchemy.gateway.broker.service.AccountMessageSender accountMessageSender,
                              MarketManager marketManager, MarketLoaderService marketLoaderService) {
        this.accountRepository = accountRepository;
        this.accountAssetRepository = accountAssetRepository;
        this.assetTransferRepository = assetTransferRepository;
        this.depositWithdrawRepository = depositWithdrawRepository;
        this.assetCursorRepository = assetCursorRepository;
        this.accountMessageSender = accountMessageSender;
        this.marketManager = marketManager;
        this.exchangeManager = exchangeManager;
        this.redisAccountHash = redisAccountHash;
        marketManager.load(marketLoaderService);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createAccount(String exchangeName, String accountId, String accessKey, String secretKey) {
        Optional<Account> byAccountIdAndStatusLessThan = accountRepository.findByAccountIdAndStatusLessThan(accountId, AccountStatusEnum.DELETED);
        Account account;
        account = byAccountIdAndStatusLessThan.orElseGet(Account::new);//应主平台账户管理要求,如第一次账户创建成功后网关处理消息异常,会重新发送账户创建消息
        account.setAccountId(accountId);
        account.setStatus(AccountStatusEnum.ENABLED);
        accountRepository.saveAndFlush(account);

        AccountInfo accountInfo = getAccountInfo(account.getId());
        //初始化账户资金转移记录
        CursorVo assetTransfersCursor = initAssetTransfers(accountInfo, account, CursorVo.builder().build());
        if (assetTransfersCursor != null) {
            AssetCursor assetCursor1 = new AssetCursor();//储存最新的资产转移记录游标
            assetCursor1.setAccountId(account.getId());
            assetCursor1.setRecordId(assetTransfersCursor.getRecordId());
            assetCursor1.setTime(assetTransfersCursor.getTime());
            assetCursor1.setType(AssetCursorType.ASSET_TRANSFER);
            assetCursorRepository.save(assetCursor1);
        }

        //初始化充提记录
        CursorVo depositWithdrawsCursor = initDepositWithdraws(accountInfo, account, CursorVo.builder().build());
        if (depositWithdrawsCursor != null) {
            AssetCursor assetCursor2 = new AssetCursor();//储存最新的充提记录转移游标
            assetCursor2.setAccountId(account.getId());
            assetCursor2.setRecordId(depositWithdrawsCursor.getRecordId());
            assetCursor2.setTime(depositWithdrawsCursor.getTime());
            assetCursor2.setType(AssetCursorType.DEPOSIT_WITHDRAW);
            assetCursorRepository.save(assetCursor2);
        }
        List<AccountAssetResp> userAsset = exchangeManager.getAPI(accountInfo.getExchangeName()).getAccountApi().findUserAsset(accountInfo.getCredentials());
        updateAsset(userAsset, account.getId(), account.getAccountId());
    }

    private CursorVo initAssetTransfers(AccountInfo accountInfo, Account account, CursorVo cursorVo) {
        AccountApi accountApi = exchangeManager.getAPI(accountInfo.getExchangeName()).getAccountApi();
        List<com.alchemy.gateway.core.common.Market> markets = marketManager.getMarkets(accountInfo.getExchangeName());//获取交易所币对

        AssetTransferResult assetTransfers = accountApi.findAssetTransfers(accountInfo.getCredentials(), cursorVo, markets);
        if (assetTransfers != null) {
            List<AssetTransferVo> assetTransfersList = assetTransfers.getList();
            if (assetTransfers.getCursorVo() != null && assetTransfers.getList() != null && assetTransfersList.size() > 0) {
                assetTransfersList.forEach(assetTransferVo -> {
                    AssetTransfer assetTransfer = new AssetTransfer();
                    assetTransfer.setExchangeRecordId(assetTransferVo.getExchangeRecordId());
                    assetTransfer.setAmount(assetTransferVo.getAmount());
                    assetTransfer.setAccountId(account.getId());
                    assetTransfer.setCoin(assetTransferVo.getCoin());
                    assetTransfer.setType(assetTransferVo.getType());
                    assetTransfer.setFromId(assetTransferVo.getFrom() != null ? assetTransferVo.getFrom() : "");
                    assetTransfer.setToId(assetTransferVo.getTo() != null ? assetTransferVo.getTo() : "");
                    assetTransfer.setCreatedAt(assetTransferVo.getCreatedAt());
                    assetTransferRepository.save(assetTransfer);
                });
                return initAssetTransfers(accountInfo, account, assetTransfers.getCursorVo());
            }
        }
        return cursorVo;
    }

    private CursorVo initDepositWithdraws(AccountInfo accountInfo, Account account, CursorVo cursorVo) {
        AccountApi accountApi = exchangeManager.getAPI(accountInfo.getExchangeName()).getAccountApi();
        DepositWithdrawResult depositWithdraws = accountApi.findDepositWithdraws(accountInfo.getCredentials(), cursorVo);
        if (depositWithdraws != null) {
            List<DepositWithdrawVo> depositWithdrawsList = depositWithdraws.getList();
            if (depositWithdraws.getCursorVo() != null && depositWithdraws.getList() != null && depositWithdrawsList.size() > 0) {
                depositWithdrawsList.forEach(depositWithdrawVo -> {
                    DepositWithdraw depositWithdraw = new DepositWithdraw();
                    depositWithdraw.setExchangeRecordId(depositWithdrawVo.getExchangeRecordId());
                    depositWithdraw.setAccountId(account.getId());
                    depositWithdraw.setCoin(depositWithdrawVo.getCoin());
                    depositWithdraw.setAmount(depositWithdrawVo.getAmount());
                    depositWithdraw.setType(depositWithdrawVo.getType());
                    depositWithdraw.setState(depositWithdrawVo.getState());
                    depositWithdraw.setFee(depositWithdrawVo.getFee());
                    depositWithdraw.setCreatedAt(depositWithdrawVo.getCreatedAt());
                    depositWithdrawRepository.save(depositWithdraw);
                });
                return initDepositWithdraws(accountInfo, account, depositWithdraws.getCursorVo());
            }
        }
        return cursorVo;
    }

    @Override
    public void updateAsset(List<AccountAssetResp> userAsset, Long id, String mianAccountId) {
        List<AccountAssetCoinVo> assets = new ArrayList<>();
        userAsset.forEach(accountAssetResp -> {
            Optional<AccountAsset> accountAssetOptional = accountAssetRepository.findByAccountIdAndCoin(id, accountAssetResp.getCoin().toUpperCase());
            if (!accountAssetOptional.isPresent()) {
                AccountAsset accountAsset = new AccountAsset();
                accountAsset.setAccountId(id);
                accountAsset.setBalance(accountAssetResp.getBalance());
                accountAsset.setCoin(accountAssetResp.getCoin().toUpperCase());
                accountAsset.setFrozen(accountAssetResp.getFrozen());
                accountAssetRepository.save(accountAsset);

                assets.add(AccountAssetCoinVo.builder()
                        .coin(accountAsset.getCoin())
                        .balance(accountAsset.getBalance().toPlainString())
                        .frozen(accountAsset.getFrozen().toPlainString())
                        .build());
            } else {
                AccountAsset accountAsset = accountAssetOptional.get();
                    accountAsset.setBalance(accountAssetResp.getBalance());
                    accountAsset.setFrozen(accountAssetResp.getFrozen());
                    accountAssetRepository.save(accountAsset);
                    assets.add(AccountAssetCoinVo.builder()
                            .coin(accountAsset.getCoin())
                            .balance(accountAsset.getBalance().toPlainString())
                            .frozen(accountAsset.getFrozen().toPlainString())
                            .build());
            }
        });
        if (assets.size() > 0) {//暂时不发送消息
            accountMessageSender.sendAccountAssetMessage(AccountAssetUpdateMessage.builder()
                    .accountId(mianAccountId).assets(assets).build());
        }
    }

    private String ensureNotNullOrThrow(@Nullable String value, String exceptionMessage) {
        if (value == null) {
            throw new CredentialsNotExistException(exceptionMessage);
        }
        return value;
    }

    @Override
    public List<Account> findAllEnabledAccounts() {
        return accountRepository.findByStatusLessThan(AccountStatusEnum.DELETED);
    }

    public AccountInfo getAccountInfo(Long id) {
        Optional<Account> byId = accountRepository.findById(id);
        if (!byId.isPresent()) {
            throw new AccountNotExistExecution("账户不存在");
        }
        Map<String, String> entries = redisAccountHash.entries("ACCOUNT:" + byId.get().getAccountId());
        String exchangeName = ensureNotNullOrThrow(entries.get("exchange_name"), "获取账户信息时数据错误: exchange_name 值为 null");
        String accessKey = ensureNotNullOrThrow(entries.get("access_key"), "获取账户信息时数据错误: access_key 值为 null");
        String secretKey = ensureNotNullOrThrow(entries.get("secret_key"), "获取账户信息时数据错误: secret_key 值为 null");
        String passphrase = entries.get("passphrase");

        return AccountInfo.of(exchangeName, Credentials.of(accessKey, secretKey, passphrase));
    }

    public Account findByAccountId(String accountId) {
        Optional<Account> optional = accountRepository.findByAccountId(accountId);
        if (!optional.isPresent()) {
            throw new AccountNotExistExecution("账户不存在");
        }
        return optional.get();
    }

    @Override
    public void deleted(String accountId) {
        Optional<Account> optional = accountRepository.findByAccountIdAndStatusLessThan(accountId, AccountStatusEnum.DELETED);
        if (!optional.isPresent()) {
            throw new AccountNotExistExecution("删除账户不存在");
        }
        Account account = optional.get();
        account.setStatus(AccountStatusEnum.DELETED);
        accountRepository.save(account);
    }

    @Override
    public void enabled(String accountId) {
        Optional<Account> optional = accountRepository.findByAccountIdAndStatusLessThan(accountId, AccountStatusEnum.DELETED);
        if (!optional.isPresent()) {
            throw new AccountNotExistExecution("开启账户不存在");
        }
        Account account = optional.get();
        account.setStatus(AccountStatusEnum.ENABLED);
        accountRepository.save(account);
    }

    @Override
    public void disabled(String accountId) {
        Optional<Account> optional = accountRepository.findByAccountIdAndStatusLessThan(accountId, AccountStatusEnum.DELETED);
        if (!optional.isPresent()) {
            throw new AccountNotExistExecution("禁用账户不存在");
        }
        Account account = optional.get();
        account.setStatus(AccountStatusEnum.DISABLED);
        accountRepository.save(account);
    }

    @Override
    public void assetSynchronizing(String accountId) {
        Optional<Account> byAccountId = accountRepository.findByAccountId(accountId);
        if (!byAccountId.isPresent()) {
            throw new AccountNotExistExecution("需要同步资产账户不存在");
        }
        AccountInfo accountInfo = getAccountInfo(byAccountId.get().getId());
        List<AccountAssetResp> userAsset = exchangeManager.getAPI(accountInfo.getExchangeName()).getAccountApi().findUserAsset(accountInfo.getCredentials());
        updateAsset(userAsset, byAccountId.get().getId(), byAccountId.get().getAccountId());
    }

    @Override
    public void errorResolved(String accountId) {
        Optional<Account> optional = accountRepository.findByAccountIdAndStatusLessThan(accountId, AccountStatusEnum.DELETED);
        if (!optional.isPresent()) {
            throw new AccountNotExistExecution("异常消除账户不存在");
        }
        Account account = optional.get();
        if (account.getStatus() == AccountStatusEnum.ASSET_ABNORMAL)
            account.setStatus(AccountStatusEnum.ENABLED);
        accountRepository.save(account);
    }

    @Override
    public AssetCursor getAssetCursor(Long accountId, AssetCursorType assetCursorType) {
        List<AssetCursor> byAccountIdAndType = assetCursorRepository.findByAccountIdAndTypeOrderByIdDesc(accountId, assetCursorType);
        if (byAccountIdAndType != null && byAccountIdAndType.size() > 0) {
            for (int i = 1; i < byAccountIdAndType.size(); i++) {
                AssetCursor assetCursor = byAccountIdAndType.get(i);
                assetCursorRepository.delete(assetCursor);
            }
            return byAccountIdAndType.get(0);
        }
        return null;
    }

    //    @Scheduled(cron = "0/5 * * * * ?")//TODO:资产应要求只在账户创建时更新和mq消息主动更新,不做实时更新
//    public void findAccountAssetUpdate() {
//        List<Account> accounts = findAllEnabledAccounts();
//        accounts.forEach(account -> {
//            AccountInfo accountInfo = getAccountInfo(account.getId());
//            List<AccountAssetResp> userAsset = exchangeManager.getAPI(accountInfo.getExchangeName()).getAccountApi().findUserAsset(accountInfo.getCredentials());
//            updateAsset(userAsset, account.getId(), account.getAccountId());
//        });
//    }
}
