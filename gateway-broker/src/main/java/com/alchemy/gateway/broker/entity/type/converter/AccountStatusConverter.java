package com.alchemy.gateway.broker.entity.type.converter;


import com.alchemy.gateway.broker.entity.type.AccountStatusEnum;

import javax.persistence.AttributeConverter;

/**
 * @author zoulingwei
 */
public class AccountStatusConverter implements AttributeConverter<AccountStatusEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AccountStatusEnum accountStatusEnum) {
        if (accountStatusEnum == null) {
            return null;
        }
        return accountStatusEnum.getIntValue();
    }

    @Override
    public AccountStatusEnum convertToEntityAttribute(Integer s) {
        return AccountStatusEnum.valueOf(s);
    }
}
