package com.kevin.gateway.okexapi.option.domain;

import com.kevin.gateway.core.CoinPair;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@Data
public class OptionBatchCancelOrderRequest {
    /**
     * 合约标的指数，如BTC-USD，同一批量撤单必须为同一标的合约
     */
    private CoinPair underlying;
    /**
     * order_id和client_oid必须且只能选一个填写。订单ID的列表。
     */
    @Nullable
    private List<String> orderIds;
    /**
     * order_id和client_oid必须且只能选一个填写。client_oid的列表，
     * 在下单时由您设置的ID来识别您的订单, 类型为字母（大小写）+数字或者纯字母（大小写） 1-32位字符
     */
    @Nullable
    private List<String> clientOids;
}
