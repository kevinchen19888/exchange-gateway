package com.kevin.gateway.okexapi.base.websocket.response;

import com.kevin.gateway.core.Symbolic;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.util.regex.Pattern;

/**
 * 币种
 */
@Data(staticConstructor = "of")
public final class ChannelFilter implements Symbolic {
    private final String symbol;
    private final static Pattern channelFilterPattern = Pattern.compile("^[a-z0-9A-Z_-]+$");  // 币种符号必须是全部大小

    @JsonCreator
    public static ChannelFilter fromSymbol(String symbol) {
        if (!channelFilterPattern.matcher(symbol).matches()) {
            throw new IllegalArgumentException("channel filter格式不对, 实际传入 " + symbol);
        }
        return of(symbol);
    }
}
