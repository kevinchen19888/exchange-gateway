package com.alchemy.gateway.core.order.tracker;

import com.alchemy.gateway.core.common.Credentials;
import com.alchemy.gateway.core.order.OrderVo;
import com.alchemy.gateway.core.order.OrderStatusCallback;
import lombok.Data;

@Data(staticConstructor = "of")
public class TrackingOrder {
    private final OrderVo orderVo;
    private final Credentials credentials;
    private final OrderStatusCallback callback;
}
