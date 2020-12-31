package com.kevin.gateway.okexapi.base.websocket.request;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.Symbolic;
import com.kevin.gateway.okexapi.future.FutureMarketId;
import com.kevin.gateway.okexapi.index.IndexInstrumentId;
import com.kevin.gateway.okexapi.option.OptionMarketId;
import com.kevin.gateway.okexapi.spot.SpotMarketId;
import com.kevin.gateway.okexapi.swap.SwapMarketId;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 订阅主题
 */
@Data
public class SubscriptionTopic {
    private final ChannelTrait channelTrait;
    @Nullable
    private final Symbolic symbolic;

    private SubscriptionTopic(ChannelTrait channelTrait, @Nullable Symbolic symbolic) {
        if (!channelTrait.acceptFilterObject(symbolic)) {
            throw new IllegalArgumentException(
                    MessageFormat.format("频道 {} 与符号 {} 类型不匹配", channelTrait.getSymbol(), symbolic));
        }

        this.channelTrait = channelTrait;
        this.symbolic = symbolic;
    }

    public static SubscriptionTopic of(ChannelTrait channelTrait, @Nullable Symbolic symbolic) {
        return new SubscriptionTopic(channelTrait, symbolic);
    }

    public String toArgString() {
        return symbolic == null
                ? String.format("%s", channelTrait.getSymbol())
                : String.format("%s:%s", channelTrait.getSymbol(), symbolic.getSymbol());

    }
    private final static Pattern CONTRACT_FORMAT = Pattern.compile("^([A-Za-z]+)-([A-Za-z]+)-([0-9]+)$");
    private final static Pattern CHANNEL_FIELTER_PATTERN = Pattern.compile("^([a-z0-9_/]+):([a-z0-9A-Z_-]+)$");
    // 正常格式："BTC-USDT"
    private final static Pattern coinPairSymbolFormat = Pattern.compile("^([A-Z]+)-([A-Z]+)$");
    @JsonCreator
    public static SubscriptionTopic fromSymbol(String symbol){
        Matcher matcher = CHANNEL_FIELTER_PATTERN.matcher(symbol);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("非法业务频道格式: " + symbol);
        }
        String channelTraitInfo = matcher.group(1);
        String filterInfo = matcher.group(2);
        Matcher coinPairMatcher = coinPairSymbolFormat.matcher(filterInfo);
        boolean isCoinPair = coinPairMatcher.matches();
        Matcher contractMatcher = CONTRACT_FORMAT.matcher(filterInfo);
        boolean isContract = contractMatcher.matches();
        Symbolic symbol1 = null;
        switch(ChannelTrait.fromSymbol(channelTraitInfo).getChannelBusiness()){
            case SPOT:
                symbol1 = isCoinPair? SpotMarketId.fromSymbol(filterInfo): Coin.of(filterInfo);
                break;
            case FUTURE:
                symbol1 = isCoinPair? CoinPair.of(filterInfo) : isContract ? FutureMarketId.fromSymbol(filterInfo) : Coin.of(filterInfo);
                break;
            case SWAP:
                symbol1 = SwapMarketId.fromSymbol(filterInfo);
                break;
            case INDEX:
                symbol1 = IndexInstrumentId.fromSymbol(filterInfo);
                break;
            case OPTION:
                symbol1 = OptionMarketId.fromSymbol(filterInfo);
                break;
        }
        return new SubscriptionTopic(ChannelTrait.fromSymbol(channelTraitInfo), symbol1);
    }
}
