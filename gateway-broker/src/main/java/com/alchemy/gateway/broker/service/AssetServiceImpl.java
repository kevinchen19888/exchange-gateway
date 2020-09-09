package com.alchemy.gateway.broker.service;

import com.alchemy.gateway.broker.entity.*;
import com.alchemy.gateway.broker.entity.type.AccountStatusEnum;
import com.alchemy.gateway.broker.entity.type.AlertErrorTypeEnum;
import com.alchemy.gateway.broker.entity.type.AlertLevelEnum;
import com.alchemy.gateway.broker.entity.type.AssetCursorType;
import com.alchemy.gateway.broker.repository.*;
import com.alchemy.gateway.broker.vo.AlertReportMessage;
import com.alchemy.gateway.broker.vo.AssetTransferMessage;
import com.alchemy.gateway.broker.vo.DepositWithdrawMessage;
import com.alchemy.gateway.core.ExchangeApi;
import com.alchemy.gateway.core.ExchangeManager;
import com.alchemy.gateway.core.ExchangeManagerImpl;
import com.alchemy.gateway.core.common.AccountInfo;
import com.alchemy.gateway.core.order.HistoryOrderResult;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.core.wallet.AccountApi;
import com.alchemy.gateway.core.wallet.AssetTransferResult;
import com.alchemy.gateway.core.wallet.CursorVo;
import com.alchemy.gateway.core.wallet.DepositWithdrawResult;
import com.alchemy.gateway.market.MarketManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.client.WebSocketClient;

import java.util.List;
import java.util.Optional;

/**
 * describe:资产相关服务
 *
 * @author zoulingwei
 */
@EnableScheduling
@Component
@Slf4j
public class AssetServiceImpl {

    @Value("${gateway.order.node}")
    private String node;
    private final OrderRepository orderRepository;
    private final AssetCursorRepository assetCursorRepository;
    private final AssetTransferRepository assetTransferRepository;
    private final DepositWithdrawRepository depositWithdrawRepository;
    private final AlertRepository alertRepository;
    private final AccountMessageSender accountMessageSender;
    private final ExchangeManager exchangeManager;
    private final MarketManager marketManager;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Autowired
    public AssetServiceImpl(OrderRepository orderRepository, AssetCursorRepository assetCursorRepository, AssetTransferRepository assetTransferRepository, DepositWithdrawRepository depositWithdrawRepository, AlertRepository alertRepository, AccountMessageSender accountMessageSender, AccountService accountService, RestTemplate restTemplate, WebSocketClient webSocketClient, MarketManager marketManager, AccountRepository accountRepository, MarketLoaderService marketLoaderService) {
        this.orderRepository = orderRepository;
        this.assetCursorRepository = assetCursorRepository;
        this.assetTransferRepository = assetTransferRepository;
        this.depositWithdrawRepository = depositWithdrawRepository;
        this.alertRepository = alertRepository;
        this.accountMessageSender = accountMessageSender;
        this.accountService = accountService;
        this.marketManager = marketManager;
        this.accountRepository = accountRepository;
        this.exchangeManager = new ExchangeManagerImpl(restTemplate, webSocketClient);
        marketManager.load(marketLoaderService);
    }

    /**
     * 校验用户的委托单是否是我们平台操作的(时间从用户创建时间开始)
     */
    @Scheduled(cron = "1 * * * * ?")
    public void assetErrorHistoryOrderCheck() {
        List<Account> accounts = accountService.findAllEnabledAccounts();//获取开启账户列表
        if (accounts != null) {
            accounts.forEach(account -> {
                AccountInfo accountInfo = accountService.getAccountInfo(account.getId());//获取凭证
                ExchangeApi api = exchangeManager.getAPI(accountInfo.getExchangeName());//获取api
                AssetCursor assetCursor = accountService.getAssetCursor(account.getId(), AssetCursorType.HISTORY_ORDER);//获取游标

                Boolean flag = true;
                List<com.alchemy.gateway.core.common.Market> markets = marketManager.getMarkets(accountInfo.getExchangeName());//获取交易所币对
                for (com.alchemy.gateway.core.common.Market market : markets) {//所有市场都需要查询一次
                    HistoryOrderResult historyOrder = api.getOrderApi().getHistoryOrder(accountInfo.getCredentials(),
                            assetCursor != null ?
                                    CursorVo.builder()
                                            .recordId(assetCursor.getRecordId())
                                            .time(assetCursor.getTime()).build() :
                                    CursorVo.builder()
                                            .recordId(null)
                                            .time(DateUtils.getEpochMilliByTime(account.getCreateAt())).build()
                            , market);//请求历史记录
                    String s = historyOrder != null ? historyOrder.toString() : "null";
                    log.info("账户:" + account.toString() + "请求委托单结果:" + s);
                    if (historyOrder != null && historyOrder.getCursorVo() != null && historyOrder.getList().size() > 0) {
                        if (flag && assetCursor != null) {//判断之前游标是否存在
                            assetCursor.setRecordId(historyOrder.getCursorVo().getRecordId());
                            assetCursor.setTime(historyOrder.getCursorVo().getTime());
                            assetCursorRepository.save(assetCursor);//更新最新的游标
                            flag = false;//只保存单词循环中第一个游标
                        } else if (flag) {
                            AssetCursor assetCursor1 = new AssetCursor();
                            assetCursor1.setAccountId(account.getId());
                            assetCursor1.setRecordId(historyOrder.getCursorVo().getRecordId());
                            assetCursor1.setTime(historyOrder.getCursorVo().getTime());
                            assetCursor1.setType(AssetCursorType.HISTORY_ORDER);
                            assetCursorRepository.save(assetCursor1);//更新最新的游标
                            flag = false;//只保存单词循环中第一个游标
                        }
                        historyOrder.getList().forEach(orderVo -> {
                            //查询之前记录是否存在
                            Optional<Order> orderOptional = orderRepository.findByExchangeOrderIdAndAccountId(orderVo.getExchangeOrderId(), account.getId());
                            if (!orderOptional.isPresent() || orderOptional.get().getState() != orderVo.getOrderState()) {//历史订单不存在或者订单状态不一致说明,有非平台订单存在,发出警报
                                Alert alert = new Alert();
                                alert.setAccountId(Long.valueOf(account.getAccountId()));
                                alert.setLevel(AlertLevelEnum.FATAL_ERROR);
                                alert.setText("出现非平台操作历史订单");
                                Alert saveAlert = alertRepository.save(alert);//存储警报信息
                                accountMessageSender.sendAccountAlertReportMessage(
                                        AlertReportMessage.builder()
                                                .accountId(String.valueOf(saveAlert.getAccountId()))
                                                .level(saveAlert.getLevel().getIntValue())
                                                .errorCode(AlertErrorTypeEnum.ORDER.getIntValue())
                                                .errorText(saveAlert.getText())
                                                .reporter(node)
                                                .build());
                            }
                        });
                    }
                }
            });
        }

    }

    /**
     * 查询是否有最新的充提记录
     */
    @Scheduled(cron = "1 * * * * ?")
    public void assetErrorDepositWithdrawCheck() {
        List<Account> accounts = accountService.findAllEnabledAccounts();//获取开启账户列表
        if (accounts != null) {
            accounts.forEach(account -> {
                AccountInfo accountInfo = accountService.getAccountInfo(account.getId());//获取凭证
                AccountApi accountApi = exchangeManager.getAPI(accountInfo.getExchangeName()).getAccountApi();//获取api

                AssetCursor assetCursor1 = accountService.getAssetCursor(account.getId(), AssetCursorType.DEPOSIT_WITHDRAW);//获取游标

                DepositWithdrawResult depositWithdraws = accountApi.findDepositWithdraws(
                        accountInfo.getCredentials(), assetCursor1 != null ?
                                CursorVo.builder()
                                        .recordId(assetCursor1.getRecordId())
                                        .time(assetCursor1.getTime()).build() : null
                );
                String s = depositWithdraws != null ? depositWithdraws.toString() : "null";
                log.info("账户:" + account.toString() + "请求充提记录结果:" + s);
                if (depositWithdraws != null && depositWithdraws.getCursorVo() != null && depositWithdraws.getList().size() > 0) {
                    if (assetCursor1 != null) {//判断之前游标是否存在
                        assetCursor1.setRecordId(depositWithdraws.getCursorVo().getRecordId());
                        assetCursor1.setTime(depositWithdraws.getCursorVo().getTime());
                        assetCursorRepository.save(assetCursor1);//更新最新的游标
                    } else {
                        AssetCursor assetCursor = new AssetCursor();
                        assetCursor.setAccountId(account.getId());
                        assetCursor.setRecordId(depositWithdraws.getCursorVo().getRecordId());
                        assetCursor.setTime(depositWithdraws.getCursorVo().getTime());
                        assetCursor.setType(AssetCursorType.DEPOSIT_WITHDRAW);
                        assetCursorRepository.save(assetCursor);//更新最新的游标
                    }
                    depositWithdraws.getList().forEach(depositWithdrawVo -> {
                        //查询之前记录是否存在
                        Optional<DepositWithdraw> depositWithdrawOptional = depositWithdrawRepository.findByAccountIdAndExchangeRecordIdAndType(account.getId(), depositWithdrawVo.getExchangeRecordId(), depositWithdrawVo.getType());
                        DepositWithdraw depositWithdraw;
                        if (depositWithdrawOptional.isPresent()) {//记录存在
                            depositWithdraw = depositWithdrawOptional.get();
                            if (depositWithdraw.getState() != depositWithdrawVo.getState()) {//判断记录状态是否变更
                                depositWithdraw.setState(depositWithdrawVo.getState());//更新状态
                                depositWithdrawRepository.save(depositWithdraw);
                            }
                        } else {//记录不存在,记录新的记录
                            depositWithdraw = new DepositWithdraw();
                            depositWithdraw.setAccountId(account.getId());
                            depositWithdraw.setAmount(depositWithdrawVo.getAmount());
                            depositWithdraw.setCoin(depositWithdrawVo.getCoin());
                            depositWithdraw.setCreatedAt(depositWithdrawVo.getCreatedAt());
                            depositWithdraw.setExchangeRecordId(depositWithdrawVo.getExchangeRecordId());
                            depositWithdraw.setFee(depositWithdrawVo.getFee());
                            depositWithdraw.setState(depositWithdrawVo.getState());
                            depositWithdraw.setType(depositWithdrawVo.getType());
                            depositWithdrawRepository.save(depositWithdraw);

                            account.setStatus(AccountStatusEnum.ASSET_ABNORMAL);//修改用户状态为资产异常
                            accountRepository.save(account);

                        }
                        accountMessageSender.sendAccountDepositWithdrawMessage(//发送消息:充提记录状态变更
                                DepositWithdrawMessage.builder()
                                        .accountId(depositWithdraw.getAccountId())
                                        .amount(String.valueOf(depositWithdraw.getAmount()))
                                        .coin(depositWithdraw.getCoin())
                                        .createdAt(depositWithdraw.getCreatedAt())
                                        .exchangeRecordId(depositWithdraw.getExchangeRecordId())
                                        .fee(String.valueOf(depositWithdraw.getFee()))
                                        .id(depositWithdraw.getId())
                                        .state(depositWithdraw.getState().getIntValue())
                                        .type(depositWithdraw.getType().getIntValue())
                                        .build());
                    });
                }
            });
        }
    }

    /**
     * 查询是否有最新的资产转移
     */
    @Scheduled(cron = "1 * * * * ?")
    public void assetErrorAssetTransferCheck() {
        List<Account> accounts = accountService.findAllEnabledAccounts();//获取开启账户列表
        if (accounts != null) {
            accounts.forEach(account -> {
                AccountInfo accountInfo = accountService.getAccountInfo(account.getId());//获取凭证
                AccountApi accountApi = exchangeManager.getAPI(accountInfo.getExchangeName()).getAccountApi();//获取api
                AssetCursor assetCursor1 = accountService.getAssetCursor(account.getId(), AssetCursorType.ASSET_TRANSFER);//获取游标

                List<com.alchemy.gateway.core.common.Market> markets = marketManager.getMarkets(accountInfo.getExchangeName());//获取交易所币对

                AssetTransferResult assetTransfers = accountApi.findAssetTransfers(accountInfo.getCredentials(),
                        assetCursor1 != null ?
                                CursorVo.builder()
                                        .recordId(assetCursor1.getRecordId())
                                        .time(assetCursor1.getTime()).build() : null, markets);
                String s = assetTransfers != null ? assetTransfers.toString() : "null";
                log.info("账户:" + account.toString() + "请求资产转移结果:" + s);
                if (assetTransfers != null && assetTransfers.getList().size() > 0 && assetTransfers.getCursorVo() != null) {
                    if (assetCursor1 != null) {//判断之前游标是否存在
                        assetCursor1.setRecordId(assetTransfers.getCursorVo().getRecordId());
                        assetCursor1.setTime(assetTransfers.getCursorVo().getTime());
                        assetCursorRepository.save(assetCursor1);//更新最新的游标
                    } else {
                        AssetCursor assetCursor = new AssetCursor();
                        assetCursor.setAccountId(account.getId());
                        assetCursor.setRecordId(assetTransfers.getCursorVo().getRecordId());
                        assetCursor.setTime(assetTransfers.getCursorVo().getTime());
                        assetCursor.setType(AssetCursorType.ASSET_TRANSFER);
                        assetCursorRepository.save(assetCursor);//更新最新的游标
                    }
                    assetTransfers.getList().forEach(assetTransferVo -> {
                        //查询之前记录是否存在
                        Optional<AssetTransfer> assetTransferOptional = assetTransferRepository.findByAccountIdAndExchangeRecordIdAndType(account.getId(), assetTransferVo.getExchangeRecordId(), assetTransferVo.getType());
                        if (!assetTransferOptional.isPresent()) {//资产转移记录不会变化,只有不存在时才需要存储
                            AssetTransfer assetTransfer = new AssetTransfer();
                            assetTransfer.setAccountId(account.getId());
                            assetTransfer.setAmount(assetTransferVo.getAmount());
                            assetTransfer.setCoin(assetTransferVo.getCoin());
                            assetTransfer.setCreatedAt(assetTransferVo.getCreatedAt());
                            assetTransfer.setExchangeRecordId(assetTransferVo.getExchangeRecordId());
                            assetTransfer.setFromId(assetTransferVo.getFrom());
                            assetTransfer.setToId(assetTransferVo.getTo());
                            assetTransfer.setType(assetTransferVo.getType());
                            assetTransferRepository.save(assetTransfer);
                            accountMessageSender.sendAccountAssetTransferMessage(//发送消息:资产转移记录
                                    AssetTransferMessage.builder()
                                            .accountId(assetTransfer.getAccountId())
                                            .amount(String.valueOf(assetTransfer.getAmount()))
                                            .coin(assetTransfer.getCoin())
                                            .createdAt(assetTransfer.getCreatedAt())
                                            .exchangeRecordId(assetTransfer.getExchangeRecordId())
                                            .from(assetTransfer.getFromId())
                                            .id(assetTransfer.getId())
                                            .to(assetTransfer.getToId())
                                            .type(assetTransfer.getType())
                                            .build());
                        }
                    });
                }
            });
        }
    }

}
