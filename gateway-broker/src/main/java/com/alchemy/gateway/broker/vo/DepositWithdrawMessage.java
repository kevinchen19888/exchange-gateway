package com.alchemy.gateway.broker.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * describe: 充提记录消息
 */
@Data
@Builder
public class DepositWithdrawMessage {
    /**
     * 主键
     */
    @JsonProperty("id")
    private Long id;
    /**
     * 平台用户id
     */
    @JsonProperty("account_id")
    private Long accountId;
    /**
     * 交易所充提记录id
     */
    @JsonProperty("exchange_record_id")
    private String exchangeRecordId;
    /**
     * 交易所充提记录类型1:充值2:提币
     */
    @JsonProperty("type")
    private int type;
    /**
     * 充提币种
     */
    @JsonProperty("coin")
    private String coin;
    /**
     * 充提数量
     */
    @JsonProperty("amount")
    private String amount;
    /**
     * 充提手续费
     */
    @JsonProperty("fee")
    private String fee;
    /**
     * 交易所充提记录状态1:处理中2:完成3失败
     */
    @JsonProperty("state")
    private int state;
    /**
     * 交易所充提记录创建时间
     */
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
}
