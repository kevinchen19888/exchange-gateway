package com.alchemy.gateway.exchangeclients.binance.util;

import com.alchemy.gateway.core.order.OrderSide;
import com.alchemy.gateway.core.order.OrderType;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * @author kevin chen
 */
public class BinanceOrderTypeConverter {

    public static String toOrderTypeStr(OrderType orderType, OrderSide side, BigDecimal stopPrice, BigDecimal price) {
        switch (orderType) {
            case LIMIT:
                return "LIMIT";
            case MARKET:
                return "MARKET";
            case STOP_LIMIT:
                if (stopPrice == null || price == null) {
                    throw new IllegalArgumentException("限价止盈/止损订单必须传入stopPrice或price");
                }
                if (OrderSide.BUY == side) {
                    if (stopPrice.compareTo(price) >= 0) {
                        return "STOP_LOSS_LIMIT";
                    }
                    if (stopPrice.compareTo(price) < 0) {
                        return "TAKE_PROFIT_LIMIT";
                    }
                } else {// 卖出
                    if (stopPrice.compareTo(price) <= 0) {
                        return "STOP_LOSS_LIMIT";
                    }
                    if (stopPrice.compareTo(price) > 0) {
                        return "TAKE_PROFIT_LIMIT";
                    }
                }
            default:
                throw new IllegalArgumentException("暂不支持的binance orderType:" + orderType);
        }
    }

    public static OrderType toOrderTypeEnum(String orderType) {
        if (StringUtils.isEmpty(orderType)) {
            throw new IllegalArgumentException("orderType不能为空");
        }
        switch (orderType) {
            case "LIMIT":
                return OrderType.LIMIT;
            case "MARKET":
                return OrderType.MARKET;
            case "STOP_LOSS_LIMIT":
            case "TAKE_PROFIT_LIMIT":
                return OrderType.STOP_LIMIT;
            default:
                throw new IllegalArgumentException("暂不支持的binance orderType:" + orderType);
        }
    }
}
