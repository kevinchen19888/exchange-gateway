package com.alchemy.gateway.exchangeclients.huobi.util;

import com.alchemy.gateway.core.order.OrderSide;
import com.alchemy.gateway.core.order.OrderType;
import org.springframework.lang.NonNull;

/**
 * @author kevin chen
 */
public class HuobiOrderTypeConverter {

    public static OrderType toOrderTypeEnum(@NonNull String orderType) {
        switch (orderType) {
            case "buy-market":
            case "sell-market":
                return OrderType.MARKET;
            case "buy-limit":
            case "sell-limit":
                return OrderType.LIMIT;
            case "buy-stop-limit":
            case "sell-stop-limit":
                return OrderType.STOP_LIMIT;
            default:
                throw new IllegalArgumentException("暂不支持的OrderType:" + orderType);
        }
    }

    public static String toOrderTypeStr(OrderType orderType, OrderSide side) {
        if (OrderSide.BUY == side) {
            switch (orderType) {
                case MARKET:
                    return "buy-market";
                case LIMIT:
                    return "buy-limit";
                case STOP_LIMIT:
                    return "buy-stop-limit";
                default:
                    throw new IllegalArgumentException("暂不支持的OrderType:" + orderType);
            }
        }
        if (OrderSide.SELL == side) {
            switch (orderType) {
                case MARKET:
                    return "sell-market";
                case LIMIT:
                    return "sell-limit";
                case STOP_LIMIT:
                    return "sell-stop-limit";
                default:
                    throw new IllegalArgumentException("暂不支持的OrderType:" + orderType);
            }
        }

        return null;
    }

    public static OrderSide toOrderSideEnum(@NonNull String orderType) {
        if (orderType.contains("buy")) {
            return OrderSide.BUY;
        }
        if (orderType.contains("sell")) {
            return OrderSide.SELL;
        }
        return null;
    }
}
