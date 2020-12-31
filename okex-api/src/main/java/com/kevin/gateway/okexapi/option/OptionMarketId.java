package com.kevin.gateway.okexapi.option;

import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.InstrumentId;
import com.kevin.gateway.core.InstrumentType;
import com.kevin.gateway.okexapi.option.util.OptionType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 期权市场标识符
 * 从Okex文档看，标识符需支持下面两处情况
 *
 * 1）
 * 公共-400档增量数据频道
 * 订阅后首次返回市场订单簿的400档深度数据并推送；后续只要订单簿深度有变化就推送有更改的数据。
 *
 * send示例
 * {"op": "subscribe", "args":["option/depth_l2_tbt:BTC-USD-200207-9500-C"]}
 *
 * 2）
 * 用户账户频道
 * 获取账户信息，需要用户登录。
 *
 * 因为标记价格变化、期权市值等变化也会引发该频道推送。
 *
 * send示例
 * {"op": "subscribe", "args": ["option/account:BTC-USD"]}
 */
@Data(staticConstructor = "of")
public class OptionMarketId implements InstrumentId {
    private final static Pattern format = Pattern.compile("^([A-Z]+)-([A-Z]+)-([0-9]+)-([0-9.]+)-([CP])$");
    private final static Pattern format2 = Pattern.compile("^([A-Z]+)-([A-Z]+)$");
    private final CoinPair coinPair;
    private final String time;
    private final String price;
    private final OptionType optionType;

    @Override
    public InstrumentType getType() {
        return InstrumentType.OPTION;
    }

    @Override
    @JsonValue
    public String getSymbol() {
        if(time!=null && price!=null) {
            return String.join("-", coinPair.getSymbol(), time, price, optionType.getVal());
        }else{
            return coinPair.getSymbol();
        }
    }

    @JsonCreator
    public static OptionMarketId fromSymbol(String symbol) {
        Matcher matcher = format.matcher(symbol);
        if (!matcher.matches()) {
            matcher = format2.matcher(symbol);
            if (!matcher.matches()) {
                throw new IllegalArgumentException("非法币对格式,正确格式为:币种-币种-时间-价格-期权类型 或 币种-币种, 实际传入:" + symbol);
            }else{
                MatchResult matchResult = matcher.toMatchResult();
                String baseCoinSymbol = matchResult.group(1);
                String quoteCoinSymbol = matchResult.group(2);
                CoinPair pair = CoinPair.of(baseCoinSymbol, quoteCoinSymbol);
                return of(pair, null, null, OptionType.C);
            }
        }else {
            MatchResult matchResult = matcher.toMatchResult();
            String baseCoinSymbol = matchResult.group(1);
            String quoteCoinSymbol = matchResult.group(2);
            String time = matchResult.group(3);
            String price = matchResult.group(4);
            String optionType = matchResult.group(5);
            CoinPair pair = CoinPair.of(baseCoinSymbol, quoteCoinSymbol);
            return of(pair, time, price, OptionType.fromVal(optionType));
        }
    }
}
