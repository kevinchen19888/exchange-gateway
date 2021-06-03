package com.kevin.gateway.huobiapi.spot.response.basedata;

import com.kevin.gateway.huobiapi.spot.vo.SpotSymbolsVo;
import lombok.Data;

import java.util.List;

/**
 * 获取所有交易对
 */
@Data
public class SpotSymbolsResponse {

    private String status;

    private List<SpotSymbolsVo> data;

//    @Data
//    public static class SpotSymbols {
//        private SpotSymbolsState state;//交易对状态；可能值: [online，offline,suspend] online - 已上线；offline - 交易对已下线，不可交易；suspend -- 交易暂停；pre-online - 即将上线
//
//        @JsonProperty(value = "base-currency")
//        private SpotCoin baseCurrency;//交易对中的基础币种
//        @JsonProperty(value = "quote-currency")
//        private SpotCoin quoteCurrency;//交易对中的报价币种
//        @JsonProperty(value = "price-precision")
//        private Integer pricePrecision;//交易对报价的精度（小数点后位数）
//        @JsonProperty(value = "amount-precision")
//        private Integer amountPrecision;//交易对基础币种计数精度（小数点后位数）
//        @JsonProperty(value = "symbol-partition")
//        private String symbolPartition;//交易区，可能值: [main，innovation]
//        private SpotMarketId symbol;//交易对
//        @JsonProperty(value = "value-precision")
//        private Integer valuePrecision;//交易对交易金额的精度（小数点后位数）
//        @JsonProperty(value = "min-order-amt")
//        private BigDecimal minOrderAmt;//交易对限价单最小下单量 ，以基础币种为单位（即将废弃）
//        @JsonProperty(value = "max-order-amt")
//        private BigDecimal maxOrderAmt;//交易对限价单最大下单量 ，以基础币种为单位（即将废弃）
//        @JsonProperty(value = "limit-order-min-order-amt")
//        private BigDecimal limitOrderMinOrderAmt;//交易对限价单最小下单量 ，以基础币种为单位（NEW）
//        @JsonProperty(value = "limit-order-max-order-amt")
//        private BigDecimal limitOrderMaxOrderAmt;//交易对限价单最大下单量 ，以基础币种为单位（NEW）
//        @JsonProperty(value = "sell-market-min-order-amt")
//        private BigDecimal sellMarketMinOrderAmt;//交易对市价卖单最小下单量，以基础币种为单位（NEW）
//        @JsonProperty(value = "sell-market-max-order-amt")
//        private BigDecimal sellMarketMaxOrderAmt;//交易对市价卖单最大下单量，以基础币种为单位（NEW）
//        @JsonProperty(value = "buy-market-max-order-value")
//        private BigDecimal buyMarketMaxOrderValue;//交易对市价买单最大下单金额，以计价币种为单位（NEW）
//        @JsonProperty(value = "min-order-value")
//        private BigDecimal minOrderValue;//交易对限价单和市价买单最小下单金额 ，以计价币种为单位
//        @JsonProperty(value = "max-order-value")
//        private BigDecimal maxOrderValue;//交易对限价单和市价买单最大下单金额 ，以折算后的USDT为单位（NEW）
//        @JsonProperty(value = "api-trading")
//        private ApiTrading apiTrading;//API交易使能标记（有效值：enabled, disabled）
//
//        @JsonProperty(value = "leverage-ratio")
//        private BigDecimal leverageRatio;//交易对杠杆最大倍数(仅对逐仓杠杆交易对、全仓杠杆交易对、杠杆ETP交易对有效）
//        private String underlying;//标的交易代码 (仅对杠杆ETP交易对有效)
//        @JsonProperty(value = "mgmt-fee-rate")
//        private BigDecimal mgmtFeeRate;//持仓管理费费率 (仅对杠杆ETP交易对有效)
//        @JsonProperty(value = "charge-time")
//        private String chargeTime;//持仓管理费收取时间 (24小时制，GMT+8，格式：HH:MM:SS，仅对杠杆ETP交易对有效)
//        @JsonProperty(value = "rebal-time")
//        private String rebalTime;//每日调仓时间 (24小时制，GMT+8，格式：HH:MM:SS，仅对杠杆ETP交易对有效)
//        @JsonProperty(value = "rebal-threshold")
//        private BigDecimal rebalThreshold;//临时调仓阈值 (以实际杠杆率计，仅对杠杆ETP交易对有效)
//        @JsonProperty(value = "init-nav")
//        private BigDecimal initNav;//初始净值（仅对杠杆ETP交易对有效）
//
//        @JsonProperty(value = "super-margin-leverage-ratio")
//        private BigDecimal superMarginLeverageRatio;
//        @JsonProperty(value = "funding-leverage-ratio")
//        private BigDecimal fundingLeverageRatio;
//    }
}
