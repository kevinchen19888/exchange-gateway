package com.alchemy.gateway.broker.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * describe:用户资产转移记录消息
 *
 * @author zoulingwei
 */
@Data
@Builder
public class AssetTransferMessage {
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
     * 交易所划转记录id
     */
    @JsonProperty("exchange_record_id")
    private String exchangeRecordId;

    /**
     * 交易所划转记录类型
     */
    @JsonProperty("type")
    private String type;
    /**
     * 划转币种
     */
    @JsonProperty("coin")
    private String coin;
    /**
     * 划转数量(转入为正,转出为负)
     */
    @JsonProperty("amount")
    private String amount;
    /**
     * 转出目标
     */
    @JsonProperty("from")
    private String from;
    /**
     * 转入目标
     */
    @JsonProperty("to")
    private String to;
    /**
     * 交易所划转记录创建时间
     */
    @JsonProperty("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
}
