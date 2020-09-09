package com.alchemy.gateway.broker.entity.type.converter;


import com.alchemy.gateway.core.order.RoleType;

import javax.persistence.AttributeConverter;

/**
 * @author zoulingwei
 */
public class RoleTypeConverter implements AttributeConverter<RoleType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(RoleType roleType) {
        if (roleType == null) {
            return null;
        }
        return roleType.getIntValue();
    }

    @Override
    public RoleType convertToEntityAttribute(Integer s) {
        return RoleType.valueOf(s);
    }
}
