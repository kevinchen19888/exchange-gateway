package com.kevin.gateway.okexapi.fundingaccount.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Destination {
    /**
     * 3:OKEx;
     */
    OKEx(3),
    /**
     * 4:数字货币地址;
     */
    COIN_ADDRESS(4),
    /**
     * 68:币全CoinAll;
     */
    COIN_ALL(68),
    /**
     * 89:OKEx Korea;
     */
    OKEX_KOREA(89),
    /**
     * 90:4A交易平台;
     */
    _4A(90),
    /**
     * 104:MY1EX;
     */
    MY1EX(104),
    /**
     * 107:BFEX;
     */
    BFEX(107),
    /**
     * 108:99EX;
     */
    _99EX(108),
    /**
     * 113:ETHEX.COM以太坊交易所;
     */
    ETHEX(113),
    /**
     * 116:雷爵爾交易所;
     */
    LIEJUEYU(116),
    /**
     * 125:PICKCOIN;
     */
    PICKCOIN(125),
    /**
     * 136:FT交易所;
     */
    FT(136),
    /**
     * 152:xFutures;
     */
    X_FUTURES(152),
    /**
     * 153:Float SV;
     */
    FLOAT_SV(153),
    /**
     * 158:理想国;
     */
    UTOPIA(158),
    /**
     * 161:币爱交易所;
     */
    COIN_LOVE(161),
    /**
     * 163:BOOMEX;
     */
    BOOMEX(163),
    /**
     * 172:币可富;
     */
    BI_KE_FU(172),
    /**
     * 173:VVCOIN;
     */
    VVCOIN(173),
    /**
     * 174:Huoblock;
     */
    HUOBLOCK(174),
    /**
     * 175:JIAMIX;
     */
    JIAMIX(175),
    /**
     * 176:币海;
     */
    BI_HAI(176),
    /**
     * 177:币尚;
     */
    BI_SHANG(177),
    /**
     * 178:七十三街交易所
     */
    _73BLOCK_EXCHANGE(178);

    private final int val;

    Destination(int val) {
        this.val = val;
    }

    @JsonValue
    public int getVal() {
        return val;
    }

    @JsonCreator
    public static Destination valueOf(int value) {
        for (Destination destination : Destination.values()) {
            if (destination.val == value) {
                return destination;
            }
        }
        throw new IllegalArgumentException("无效的提币地址值,s:" + value);
    }
}
