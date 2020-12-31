package com.kevin.gateway.okexapi.future.websocket.response;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.okexapi.future.type.MarginMode;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 账户全仓信息
 */
@Data
public class AccountFixedData implements AccountData {
    /**
     * 可划转数量
     */
    private BigDecimal canWithdraw;
    /**
     * 合约信息
     */
    private List<ContractData> contracts;
    /**
     * 账户余额币种，如：BTC,USDT
     */
    private Coin currency;
    /**
     * 账户权益（账户动态权益）
     */
    private BigDecimal equity;
    /**
     * 强平模式
     * tier（梯度强平）
     */
    private String liquiMode;
    /**
     * 账户类型
     * 逐仓：fixed
     */
    private MarginMode marginMode = MarginMode.fixed;
    /**
     * 账户数据更新时间
     */
    private LocalDateTime timestamp;
    /**
     * 账户余额（账户静态权益）
     */
    private BigDecimal totalAvailBalance;

}
