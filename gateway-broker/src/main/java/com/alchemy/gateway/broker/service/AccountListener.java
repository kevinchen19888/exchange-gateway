package com.alchemy.gateway.broker.service;

import com.alchemy.gateway.broker.config.RabbitConstants;
import com.alchemy.gateway.broker.entity.type.AlertErrorTypeEnum;
import com.alchemy.gateway.broker.entity.type.AlertLevelEnum;
import com.alchemy.gateway.broker.vo.AccountMessage;
import com.alchemy.gateway.broker.vo.AlertReportMessage;
import com.alchemy.gateway.broker.vo.BaseAccountVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * describe:
 *
 * @author zoulingwei
 */
@Component
@Slf4j
public class AccountListener {

    private final AccountMessageHandler accountMessageHandler;
    private final AccountMessageSender accountMessageSender;

    @Autowired
    public AccountListener(AccountMessageHandler accountMessageHandler, AccountMessageSender accountMessageSender) {
        this.accountMessageHandler = accountMessageHandler;
        this.accountMessageSender = accountMessageSender;
    }

    @RabbitListener(queues = RabbitConstants.QUEUE_ACCOUNT_CREATE)
    public void accountCreated(AccountMessage accountMessage) {
        log.info("队列 {} 接收到消息 {}", RabbitConstants.QUEUE_ACCOUNT_CREATE, accountMessage);
        try {
            accountMessageHandler.createAccount(accountMessage.getExchangeName(), accountMessage.getAccountId(), accountMessage.getAccessKey(), accountMessage.getSecretKey());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("用户创建消息异常:" + accountMessage + ";异常:" + e);
            accountMessageSender.sendAccountAlertReportMessage(
                    AlertReportMessage.builder()
                            .accountId(accountMessage.getAccountId())
                            .level(AlertLevelEnum.FATAL_ERROR.getIntValue())
                            .errorCode(AlertErrorTypeEnum.ACCOUNT.getIntValue())
                            .errorText("用户创建异常")
                            .reporter(e.getMessage())
                            .build());
        }
    }

    @RabbitListener(queues = RabbitConstants.QUEUE_ACCOUNT_DELETE)
    public void accountDeleted(AccountMessage accountMessage) {
        log.info("队列 {} 接收到消息 {}", RabbitConstants.QUEUE_ACCOUNT_DELETE, accountMessage);
        try {
            accountMessageHandler.deleted(accountMessage.getAccountId());
        } catch (Exception e) {
            log.error("用户删除消息异常:" + accountMessage + ";异常:" + e);
        }

    }

    @RabbitListener(queues = RabbitConstants.QUEUE_ACCOUNT_ENABLE)
    public void accountEnabled(AccountMessage accountMessage) {
        log.info("队列 {} 接收到消息 {}", RabbitConstants.QUEUE_ACCOUNT_ENABLE, accountMessage);
        try {
            accountMessageHandler.enabled(accountMessage.getAccountId());
        } catch (Exception e) {
            log.error("用户启用消息异常:" + accountMessage + ";异常:" + e);
        }
    }

    @RabbitListener(queues = RabbitConstants.QUEUE_ACCOUNT_DISABLE)
    public void accountDisabled(AccountMessage accountMessage) {
        log.info("队列 {} 接收到消息 {}", RabbitConstants.QUEUE_ACCOUNT_DISABLE, accountMessage);
        try {
            accountMessageHandler.disabled(accountMessage.getAccountId());
        } catch (Exception e) {
            log.error("用户禁用消息异常:" + accountMessage + ";异常:" + e);
        }
    }

    @RabbitListener(queues = RabbitConstants.QUEUE_ACCOUNT_SYNC_ASSET)
    public void accountAssetSynchronizing(BaseAccountVo accountMessage) {
        log.info("队列 {} 接收到消息 {}", RabbitConstants.QUEUE_ACCOUNT_SYNC_ASSET, accountMessage);
        try {
            accountMessageHandler.assetSynchronizing(accountMessage.getAccountId());
        } catch (Exception e) {
            log.error("用户资产同步消息异常:" + accountMessage + ";异常:" + e);
        }
    }

    @RabbitListener(queues = RabbitConstants.QUEUE_ACCOUNT_RESOLVE_ERROR)
    public void accountErrorResolved(AccountMessage accountMessage) {
        log.info("队列 {} 接收到消息 {}", RabbitConstants.QUEUE_ACCOUNT_RESOLVE_ERROR, accountMessage);
        try {
            accountMessageHandler.errorResolved(accountMessage.getAccountId());
        } catch (Exception e) {
            log.error("用户异常回复消息异常:" + accountMessage + ";异常:" + e);
        }
    }


}
