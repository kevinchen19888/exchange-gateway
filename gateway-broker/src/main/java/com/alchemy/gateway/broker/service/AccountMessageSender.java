package com.alchemy.gateway.broker.service;

import com.alchemy.gateway.broker.config.RabbitConstants;
import com.alchemy.gateway.broker.vo.AccountAssetUpdateMessage;
import com.alchemy.gateway.broker.vo.AlertReportMessage;
import com.alchemy.gateway.broker.vo.AssetTransferMessage;
import com.alchemy.gateway.broker.vo.DepositWithdrawMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * describe:MQ消息发送服务
 *
 * @author zoulingwei
 */
@Component
@Slf4j
public class AccountMessageSender {

    private final RabbitTemplate template;

    @Autowired
    public AccountMessageSender(RabbitTemplate template) {
        this.template = template;
    }

    /**
     * 发送用户资产信息
     *
     * @param accountAssetUpdateMessage 资产消息
     */
    void sendAccountAssetMessage(AccountAssetUpdateMessage accountAssetUpdateMessage) {
        template.convertAndSend(RabbitConstants.EXCHANGE_MINE_TOPIC,
                RabbitConstants.ROUTING_KEY_SEND_ACCOUNT_ASSET + accountAssetUpdateMessage.getAccountId(),
                accountAssetUpdateMessage);
        log.info("routingKey {} 发送消息 {}", RabbitConstants.ROUTING_KEY_SEND_ACCOUNT_ASSET, accountAssetUpdateMessage.toString());
    }

    /**
     * 发送用户异常警报
     *
     * @param alertReportMessage 异常信息
     */
    void sendAccountAlertReportMessage(AlertReportMessage alertReportMessage) {
        template.convertAndSend(RabbitConstants.EXCHANGE_MINE_TOPIC,
                RabbitConstants.ROUTING_KEY_SEND_ACCOUNT_ERROR + alertReportMessage.getAccountId(),
                alertReportMessage);
        log.info("routingKey {} 发送消息 {}", RabbitConstants.ROUTING_KEY_SEND_ACCOUNT_ERROR, alertReportMessage.toString());

    }

    /**
     * 发送账户资产转移消息
     * @param assetTransferMessage 消息
     */
    void sendAccountAssetTransferMessage(AssetTransferMessage assetTransferMessage){
        template.convertAndSend(RabbitConstants.EXCHANGE_MINE_TOPIC,
                RabbitConstants.ROUTING_KEY_SEND_ACCOUNT_TRANSFER + assetTransferMessage.getAccountId(),
                assetTransferMessage);
        log.info("routingKey {} 发送消息 {}", RabbitConstants.ROUTING_KEY_SEND_ACCOUNT_TRANSFER, assetTransferMessage.toString());

    }
    /**
     * 发送充提记录消息
     * @param assetTransferMessage 消息
     */
    void sendAccountDepositWithdrawMessage(DepositWithdrawMessage assetTransferMessage){
        template.convertAndSend(RabbitConstants.EXCHANGE_MINE_TOPIC,
                RabbitConstants.ROUTING_KEY_SEND_ACCOUNT_DEPOSIT_WITHDRAW + assetTransferMessage.getAccountId(),
                assetTransferMessage);
        log.info("routingKey {} 发送消息 {}", RabbitConstants.ROUTING_KEY_SEND_ACCOUNT_DEPOSIT_WITHDRAW, assetTransferMessage.toString());

    }

}
