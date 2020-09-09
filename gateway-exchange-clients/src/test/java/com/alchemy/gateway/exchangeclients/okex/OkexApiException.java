package com.alchemy.gateway.exchangeclients.okex;

import com.alchemy.gateway.core.ExchangeApi;
import com.alchemy.gateway.core.common.CoinPair;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.common.OrderLimitManager;
import com.alchemy.gateway.core.order.OrderRequest;
import com.alchemy.gateway.core.order.OrderSide;
import com.alchemy.gateway.core.order.OrderType;
import com.alchemy.gateway.core.wallet.CursorVo;
import com.alchemy.gateway.exchangeclients.okex.impl.OkexOrderLimit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Slf4j
public class OkexApiException {

    /**
     * okex获取历史订单的游标取值测试
     */
    @Test
    public void testOkexGetHistoryOrderRecordId() {
        long filledAlgo = Long.parseLong("5433507105692672");
        long cancelledAlgo = Long.parseLong("0");
        long entrustOrder = Long.parseLong("0");


        if (filledAlgo == 0) {
            filledAlgo = Long.MAX_VALUE;
        }
        if (cancelledAlgo == 0) {
            cancelledAlgo = Long.MAX_VALUE;
        }
        CursorVo cursorVo = CursorVo.builder().recordId(Math.min(filledAlgo, cancelledAlgo) + "," + entrustOrder).build();
        System.out.println(cursorVo);

        if (cursorVo != null && !StringUtils.isEmpty(cursorVo.getRecordId())) {
            String[] before = cursorVo.getRecordId().split(",");
            if (before.length >= 2) {
                long beforeId = Long.parseLong(before[0]);
                if (beforeId != 0 && beforeId != Long.MAX_VALUE) {
                    System.out.println("&before=" + beforeId);
                }
            }
        }
    }

    private final ExchangeApi exchangeApi = new OkexExchangeApi();

    /**
     * 下单的价格,数量检查是否符合okex的要求
     */
    @Test
    public void testOkexOrderLimit() {
        OrderRequest request = new OrderRequest(1232141234L, 1232141234L,
                Market.spotMarket(CoinPair.of("BTC", "USDT")), OrderType.MARKET, new BigDecimal("0.010000000000"),
                new BigDecimal("0.010000000000"), new BigDecimal("0.010000000000"), OrderSide.BUY, "Okex",null);

        OkexOrderLimit okexOrderLimit = (OkexOrderLimit) exchangeApi.getOrderLimitManager().getOrderLimit(exchangeApi.getName() + CoinPair.of("BTC", "USDT").toSymbol());

        if (request.getAmount() != null) {
            if (okexOrderLimit.getMinOrderAmount().compareTo(request.getAmount()) <= 0) {
                if (okexOrderLimit.getSizeIncrement().scale() < request.getAmount().stripTrailingZeros().scale()) {
                    throw new IllegalArgumentException("amount不符合OKEX的交易货币数量精度" + okexOrderLimit.getSizeIncrement().scale() + ",请注意");
                }
            } else {
                throw new IllegalArgumentException("amount小于OKEX的最小交易数量" + okexOrderLimit.getMinOrderAmount() + ",请注意");
            }
        } else {
            throw new IllegalArgumentException("amount为空,请注意");
        }

        //限价
        if (OrderType.LIMIT.equals(request.getType())) {
            if (request.getPrice() != null) {
                if (okexOrderLimit.getTickSize().scale() < request.getPrice().stripTrailingZeros().scale()) {
                    throw new IllegalArgumentException("price不符合okex的交易价格精度" + okexOrderLimit.getTickSize().scale() + ",请注意");
                }
            } else {
                throw new IllegalArgumentException("类型为" + request.getType().getName() + ",price为空,请注意");
            }
        }

        //止盈止损时
        if (OrderType.STOP_LIMIT.equals(request.getType())) {
            if (request.getStopPrice() != null) {
                if (okexOrderLimit.getTickSize().scale() < request.getStopPrice().stripTrailingZeros().scale()) {
                    throw new IllegalArgumentException("stopPrice不符合okex的交易价格精度" + okexOrderLimit.getTickSize().scale() + ",请注意");
                }
            } else {
                throw new IllegalArgumentException("类型为" + request.getType().getName() + ",stopPrice为空,请注意");
            }

        }
        System.out.println(true);
    }

    @Before
    public void initial() {
        OkexOrderLimit btc = OkexOrderLimit.builder()
                //.minSize(new BigDecimal("0.001"))
                .sizeIncrement(new BigDecimal("0.00000001"))
                .tickSize(new BigDecimal("0.1")).build();
        btc.setSymbol(CoinPair.of("BTC", "USDT").toSymbol());
        btc.setMinOrderAmount(new BigDecimal("0.001"));

        OkexOrderLimit eth = OkexOrderLimit.builder()
                //.minSize(new BigDecimal("0.001"))
                .sizeIncrement(new BigDecimal("0.000001"))
                .tickSize(new BigDecimal("0.01")).build();
        eth.setSymbol(CoinPair.of("ETH", "USDT").toSymbol());
        eth.setMinOrderAmount(new BigDecimal("0.001"));

        OrderLimitManager orderLimitManager = exchangeApi.getOrderLimitManager();
        orderLimitManager.addOrderLimit(exchangeApi.getName(), btc);
        orderLimitManager.addOrderLimit(exchangeApi.getName(), eth);
    }

}
