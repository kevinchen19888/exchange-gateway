package com.kevin.gateway.okexapi.future.service;

import com.kevin.gateway.okexapi.future.data.model.*;
import com.kevin.gateway.okexapi.future.data.model.*;
import com.kevin.gateway.okexapi.option.util.TimeGranularity;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public interface FutureDataApi {


    /**
     * (1)公共-多空持仓人数比
     此接口为公共接口，不需要身份验证。

     限速规则：20次/2s
     HTTP请求
     GET/api/information/v3/<currency>/long_short_ratio

     请求示例
     GET/api/information/v3/btc/long_short_ratio?start=2019-03-24T02:31:00Z&end=2019-03-25T02:55:00Z&granularity=300

     * @param currency  币种
     * @param start  开始时间（ISO 8601标准，例如：2018-06-20T02:31:00Z）
     * @param end    结束时间（ISO 8601标准，例如：2018-06-20T02:31:00Z）
     * @param granularity  	时间粒度，以秒为单位，默认值300。如[300/3600/86400]
     * @return  多空持仓人数比
     */
    FutureLongShortRatioResponse[] getLongShortRatio(String currency, @Nullable LocalDateTime start, @Nullable LocalDateTime end, @Nullable TimeGranularity granularity);



    /**
     * (2)公共-持仓总量及交易量
     此接口为公共接口，不需要身份验证。

     限速规则：20次/2s
     HTTP请求
     GET/api/information/v3/<currency>/volume

     请求示例
     GET/api/information/v3/btc/volume?start=2019-03-24T02:31:00Z&end=2019-03-25T02:55:00Z&granularity=300

     * @param currency  币种
     * @param start  开始时间（ISO 8601标准，例如：2018-06-20T02:31:00Z）
     * @param end    结束时间（ISO 8601标准，例如：2018-06-20T02:31:00Z）
     * @param granularity  	时间粒度，以秒为单位，默认值300。如[300/3600/86400]
     * @return 持仓总量及交易量
     */
    FutureVolumeResponse[] getVolume(String currency, @Nullable LocalDateTime start, @Nullable LocalDateTime end, @Nullable TimeGranularity granularity);


    /**
     * (3)公共-主动买入卖出情况
     此接口为公共接口，不需要身份验证。

     限速规则：20次/2s
     HTTP请求
     GET/api/information/v3/<currency>/taker

     请求示例
     GET/api/information/v3/btc/taker?start=2019-03-24T02:31:00Z&end=2019-03-25T02:55:00Z&granularity=300

     * @param currency  币种
     * @param start  开始时间（ISO 8601标准，例如：2018-06-20T02:31:00Z）
     * @param end    结束时间（ISO 8601标准，例如：2018-06-20T02:31:00Z）
     * @param granularity  	时间粒度，以秒为单位，默认值300。如[300/3600/86400]
     * @return 主动买入卖出情况
     */
    FutureTakerResponse[] getTaker(String currency, @Nullable LocalDateTime start, @Nullable LocalDateTime end, @Nullable TimeGranularity granularity);



    /**
     * (4)公共-多空精英趋向指标
     此接口为公共接口，不需要身份验证。

     限速规则：20次/2s
     HTTP请求
     GET/api/information/v3/<currency>/sentiment

     请求示例
     GET/api/information/v3/btc/sentiment?start=2019-03-24T02:31:00Z&end=2019-03-25T02:55:00Z&granularity=300

     * @param currency  币种
     * @param start  开始时间（ISO 8601标准，例如：2018-06-20T02:31:00Z）
     * @param end    结束时间（ISO 8601标准，例如：2018-06-20T02:31:00Z）
     * @param granularity  	时间粒度，以秒为单位，默认值300。如[300/3600/86400]
     * @return 多空精英趋向指标
     */
    FutureSentimentResponse[] getSentiment(String currency, @Nullable LocalDateTime start, @Nullable LocalDateTime end, @Nullable TimeGranularity granularity);


    /**
     * (5)公共-多空精英平均持仓比例
     此接口为公共接口，不需要身份验证。

     限速规则：20次/2s
     HTTP请求
     GET/api/information/v3/<currency>/margin

     请求示例
     GET/api/information/v3/btc/margin?start=2019-03-24T02:31:00Z&end=2019-03-25T02:55:00Z&granularity=300

     * @param currency  币种
     * @param start  开始时间（ISO 8601标准，例如：2018-06-20T02:31:00Z）
     * @param end    结束时间（ISO 8601标准，例如：2018-06-20T02:31:00Z）
     * @param granularity  	时间粒度，以秒为单位，默认值300。如[300/3600/86400]
     * @return 多空精英平均持仓比例
     */
    FutureMarginResponse[] getMargin(String currency, @Nullable LocalDateTime start, @Nullable LocalDateTime end, @Nullable TimeGranularity granularity);
}
