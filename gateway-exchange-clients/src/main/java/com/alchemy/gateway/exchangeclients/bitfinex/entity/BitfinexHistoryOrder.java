package com.alchemy.gateway.exchangeclients.bitfinex.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * https://docs.bitfinex.com/reference#rest-auth-submit-order
 *
 *
 *      ID,
 *      GID,
 *      CID,
 *      SYMBOL,
 *      MTS_CREATE,
 *      MTS_UPDATE,
 *      AMOUNT,
 *      AMOUNT_ORIG,
 *      TYPE,
 *      TYPE_PREV,
 *      MTS_TIF,
 *      _PLACEHOLDER,
 *      FLAGS,
 *      ORDER_STATUS,
 *      _PLACEHOLDER,
 *      _PLACEHOLDER,
 *      PRICE,
 *      PRICE_AVG,
 *      PRICE_TRAILING,
 *      PRICE_AUX_LIMIT,
 *      _PLACEHOLDER,
 *      _PLACEHOLDER,
 *      _PLACEHOLDER,
 *      HIDDEN,
 *      PLACED_ID,
 *      _PLACEHOLDER,
 *      _PLACEHOLDER,
 *      _PLACEHOLDER,
 *      ROUTING,
 *      _PLACEHOLDER,
 *      _PLACEHOLDER,
 *      META
 *
 * @Version 1.0
 *
[[49336908943,null,1597751147675,"tBTCUSD",
1597751148000,1597751362000,-0.00071,-0.00071,
"EXCHANGE LIMIT",null,null,null,"0",
"CANCELED",null,null,13000,0,0,0,null,null,null
,0,0,null,null,null,"API>BFX",null,null,{"aff_code":"AFF_CODE_HERE"}],[49335265447,null,1597747966435,"tBTCUSD",1597747966000,1597748067000,-0.00071,-0.00071,"EXCHANGE LIMIT",null,null,null,"0","CANCELED",null,null,13000,0,0,0,null,null,null,0,0,null,null,null,"API>BFX",null,null,{"aff_code":"AFF_CODE_HERE"}],[49334123821,null,1597747077802,"tBTCUSD",1597747078000,1597747949000,-0.0007,-0.0007,"EXCHANGE LIMIT",null,null,null,"0","CANCELED",null,null,13000,0,0,0,null,null,null,0,0,null,null,null,"API>BFX",null,null,{"aff_code":"AFF_CODE_HERE"}]]
 */
@Data
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BitfinexHistoryOrder {
    private Long id;
    private Object gid;
    private Long cid;
    private String symbol;
    private Long mtsCreate;
    private Long mtsUpdate;
    private BigDecimal amount;
    private BigDecimal amountOrigin;

    private String type;
    private String typePre;
    private String mtsTif;
    private String placeHolder1;
    private String flags;

    /**
     * ex: active
     */
    private String orderStatus;
    private String placeHolder2;
    private String placeHolder3;

    private BigDecimal price;
    private BigDecimal priceAvg;
    private BigDecimal priceTriggering;
    private BigDecimal  priceAuxLimit;

    private String placeHolder4;
    private String placeHolder5;
    private String placeHolder6;

    private Integer hidden;
    private Integer placeId;

    private String placeHolder7;
    private String placeHolder8;
    private String placeHolder9;

    private String routing;


    private String placeHolder10;
    private String placeHolder11;
    private ResponseMeta meta;


    @JsonCreator
    public BitfinexHistoryOrder(
            @JsonProperty("ID")    Long id,
            @JsonProperty("GID")  Object gid,
            @JsonProperty("cid") Long cid,
            @JsonProperty("symbol")  String symbol,
            @JsonProperty("mtsCreate") Long mtsCreate,
            @JsonProperty("mtsUpdate") Long mtsUpdate,
            @JsonProperty("amount") BigDecimal amount,
            @JsonProperty("amountOrigin") BigDecimal amountOrigin,
            @JsonProperty("type") String type,
            @JsonProperty("typePre") String typePre,
            @JsonProperty("mtsTif") String mtsTif,
            @JsonProperty("placeHolder1") String placeHolder1,
            @JsonProperty("flags") String flags,
            @JsonProperty("orderStatus")  String orderStatus,
            @JsonProperty("placeHolder2") String placeHolder2,
            @JsonProperty("placeHolder3") String placeHolder3,
            @JsonProperty("price")  BigDecimal price,
            @JsonProperty("priceAvg") BigDecimal priceAvg,
            @JsonProperty("priceTriggering") BigDecimal priceTriggering,
            @JsonProperty("priceAuxLimit")  BigDecimal priceAuxLimit,
            @JsonProperty("placeHolder4") String placeHolder4,
            @JsonProperty("placeHolder5") String placeHolder5,
            @JsonProperty("placeHolder6")String placeHolder6,
          //  @JsonProperty("notify") Object notify,
            @JsonProperty("hidden")Integer hidden,
            @JsonProperty("placeId")Integer placeId,
            @JsonProperty("placeHolder7")String placeHolder7,
            @JsonProperty("placeHolder8")String placeHolder8,
            @JsonProperty("placeHolder9")String placeHolder9,
            @JsonProperty("routing") String routing,
            @JsonProperty("placeHolder10")String placeHolder10,
            @JsonProperty("placeHolder11")String placeHolder11,
            @JsonProperty("meta")ResponseMeta meta
    ) {
        this.id = id;
        this.gid = gid;
        this.cid = cid;
        this.symbol = symbol;
        this.mtsCreate = mtsCreate;
        this.mtsUpdate = mtsUpdate;
        this.amount = amount;
        this.amountOrigin = amountOrigin;
        this.type = type;
        this.typePre = typePre;
        this.mtsTif = mtsTif;
        this.placeHolder1 = placeHolder1;
        this.flags = flags;
        this.orderStatus = orderStatus;
        this.placeHolder2 = placeHolder2;
        this.placeHolder3 = placeHolder3;
        this.price = price;
        this.priceAvg = priceAvg;
        this.priceTriggering = priceTriggering;
        this.priceAuxLimit = priceAuxLimit;
        this.placeHolder4 = placeHolder4;
        this.placeHolder5 = placeHolder5;
        this.placeHolder6 = placeHolder6;
     //   this.notify=notify;
        this.hidden = hidden;
        this.placeId = placeId;
        this.placeHolder7 = placeHolder7;
        this.placeHolder8 = placeHolder8;
        this.placeHolder9 = placeHolder9;
        this.routing = routing;
        this.placeHolder10 = placeHolder10;
        this.placeHolder11 = placeHolder11;
        this.meta = meta;
    }
}
