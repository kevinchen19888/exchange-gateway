package com.alchemy.gateway.broker.entity.type.converter;


import com.alchemy.gateway.core.wallet.DepositWithdrawType;

import javax.persistence.AttributeConverter;

/**
 * @author zoulingwei
 */
public class DepositWithdrawTypeConverter implements AttributeConverter<DepositWithdrawType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DepositWithdrawType type) {
        if (type == null) {
            return null;
        }
        return type.getIntValue();
    }

    @Override
    public DepositWithdrawType convertToEntityAttribute(Integer s) {
        return DepositWithdrawType.valueOf(s);
    }
}
