package com.alchemy.gateway.broker.entity.type.converter;


import com.alchemy.gateway.core.order.OrderType;

import javax.persistence.AttributeConverter;


public class OrderTypeConverter implements AttributeConverter<OrderType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrderType orderType) {
        if (orderType == null) {
            return null;
        }
        return orderType.getIntValue();
    }

    @Override
    public OrderType convertToEntityAttribute(Integer s) {
        return OrderType.valueOf(s);

    }
}