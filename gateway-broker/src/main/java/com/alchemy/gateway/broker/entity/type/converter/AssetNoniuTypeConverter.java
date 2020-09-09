package com.alchemy.gateway.broker.entity.type.converter;


import com.alchemy.gateway.broker.entity.type.AssetCursorType;

import javax.persistence.AttributeConverter;

/**
 * @author zoulingwei
 */
public class AssetNoniuTypeConverter implements AttributeConverter<AssetCursorType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(AssetCursorType assetCursorType) {
        if (assetCursorType == null) {
            return null;
        }
        return assetCursorType.getIntValue();
    }

    @Override
    public AssetCursorType convertToEntityAttribute(Integer s) {
        return AssetCursorType.valueOf(s);
    }
}
