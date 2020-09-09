package com.alchemy.gateway.broker.entity.type.converter;


import com.alchemy.gateway.core.order.OrderSide;

import javax.persistence.AttributeConverter;


public class OrderSideConverter implements AttributeConverter<OrderSide, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrderSide orderSide) {
        if (orderSide == null) {
            return null;
        }
        return orderSide.getIntValue();
    }

    @Override
    public OrderSide convertToEntityAttribute(Integer s) {
        return OrderSide.valueOf(s);

    }
}