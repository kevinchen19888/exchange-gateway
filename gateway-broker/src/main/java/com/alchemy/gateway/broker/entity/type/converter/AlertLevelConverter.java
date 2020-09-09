package com.alchemy.gateway.broker.entity.type.converter;


import com.alchemy.gateway.broker.entity.type.AlertLevelEnum;

import javax.persistence.AttributeConverter;

/**
 * @author zoulingwei
 */
public class AlertLevelConverter implements AttributeConverter<AlertLevelEnum, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AlertLevelEnum alertLevelEnum) {
        if (alertLevelEnum == null) {
            return null;
        }
        return alertLevelEnum.getIntValue();
    }

    @Override
    public AlertLevelEnum convertToEntityAttribute(Integer s) {
        return AlertLevelEnum.valueOf(s);
    }
}
