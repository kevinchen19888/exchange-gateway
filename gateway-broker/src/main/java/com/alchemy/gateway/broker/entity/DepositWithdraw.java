package com.alchemy.gateway.broker.entity;

import com.alchemy.gateway.core.wallet.DepositWithdrawState;
import com.alchemy.gateway.broker.entity.type.converter.DepositWithdrawStateConverter;
import com.alchemy.gateway.broker.entity.type.converter.DepositWithdrawTypeConverter;
import com.alchemy.gateway.core.wallet.DepositWithdrawType;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * describe:充提记录
 *
 * @author zoulingwei
 */
@Entity
@Data
@Table(name = "deposit_withdraw")
public class DepositWithdraw {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", insertable = false, nullable = false)
    private Long id;
    /**
     * 外键accountId
     */
    @Column(name = "account_id", nullable = false)
    private Long accountId;
    /**
     * 交易所充提记录id
     */
    @Column(name = "exchange_record_id", nullable = false)
    private String exchangeRecordId;
    /**
     * 交易所充提记录类型
     */
    @Column(name = "type", nullable = false)
    @Convert(converter = DepositWithdrawTypeConverter.class)
    private DepositWithdrawType type;
    /**
     * 充提币种
     */
    @Column(name = "coin", nullable = false)
    private String coin;
    /**
     * 充提数量
     */
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    /**
     * 充提手续费
     */
    @Column(name = "fee")
    private BigDecimal fee;
    /**
     * 交易所充提记录状态
     */
    @Column(name = "state")
    @Convert(converter = DepositWithdrawStateConverter.class)
    private DepositWithdrawState state;
    /**
     * 交易所充提记录创建时间
     */
    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

}
