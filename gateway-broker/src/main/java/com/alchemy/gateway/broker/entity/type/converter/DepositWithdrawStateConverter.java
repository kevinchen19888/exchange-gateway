package com.alchemy.gateway.broker.entity.type.converter;


import com.alchemy.gateway.core.wallet.DepositWithdrawState;

import javax.persistence.AttributeConverter;

/**
 * @author zoulingwei
 */
public class DepositWithdrawStateConverter implements AttributeConverter<DepositWithdrawState, Integer> {
    @Override
    public Integer convertToDatabaseColumn(DepositWithdrawState state) {
        if (state == null) {
            return null;
        }
        return state.getIntValue();
    }

    @Override
    public DepositWithdrawState convertToEntityAttribute(Integer s) {
        return DepositWithdrawState.valueOf(s);
    }
}
