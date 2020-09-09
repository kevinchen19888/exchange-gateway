package com.alchemy.gateway.broker.entity.type.converter;


import com.alchemy.gateway.core.order.OrderState;

import javax.persistence.AttributeConverter;

public class OrderStateConverter implements AttributeConverter<OrderState, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrderState orderState) {
        if (orderState == null) {
            return null;
        }
        return orderState.getIntValue();
    }

    @Override
    public OrderState convertToEntityAttribute(Integer s) {
        return OrderState.valueOf(s);

    }
}