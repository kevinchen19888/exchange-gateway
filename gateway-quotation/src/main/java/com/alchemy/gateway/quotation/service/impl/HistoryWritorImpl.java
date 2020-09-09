package com.alchemy.gateway.quotation.service.impl;

import com.alchemy.gateway.core.common.CandleInterval;
import com.alchemy.gateway.core.common.Market;
import com.alchemy.gateway.core.marketdata.CandleTick;
import com.alchemy.gateway.core.utils.DateUtils;
import com.alchemy.gateway.quotation.entity.*;
import com.alchemy.gateway.quotation.repository.KlineRepository;
import com.alchemy.gateway.quotation.service.HistoryWritor;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

@Slf4j
@Service
public class HistoryWritorImpl implements HistoryWritor {

    private final EntityManager entityManager;
    private final KlineRepository klineRepository;

    public HistoryWritorImpl(EntityManager entityManager, KlineRepository klineRepository) {
        this.entityManager = entityManager;
        this.klineRepository = klineRepository;
    }

    @Override
    @Transactional
    public void save(Kline kline, String granularity) {

        String sql = new StringBuilder().append("INSERT INTO kline_").append(granularity)
                .append(" (close_price,exchange_name,high_price,instrument_id,low_price,open_price,time_stamp,volume,reserve) ")
                .append("VALUES (").append(kline.getClose()).append(",'").append(kline.getExchangeName()).append("',")
                .append(kline.getHigh()).append(",'").append(kline.getInstrumentId()).append("',").append(kline.getLow())
                .append(",").append(kline.getOpen()).append(",'").append(kline.getTimeStamp()).append("',")
                .append(kline.getVolume()).append(",'").append(kline.getReserve()).append("')")
                .append(" ON DUPLICATE KEY UPDATE high=high").toString();
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void saveAll(List<Kline> klineList, String granularity, String exchangeName) {
        StringBuilder sql = new StringBuilder().append("INSERT INTO kline_").append(granularity)
                .append(" (close_price,exchange_name,high_price,instrument_id,low_price,open_price,time_stamp,volume,reserve) ")
                .append("VALUES ");
        for (int i = 0; i < klineList.size(); i++) {
            Kline kline = klineList.get(i);
            sql.append("(").append(kline.getClose()).append(",'").append(kline.getExchangeName()).append("',")
                    .append(kline.getHigh()).append(",'").append(kline.getInstrumentId()).append("',").append(kline.getLow())
                    .append(",").append(kline.getOpen()).append(",'").append(kline.getTimeStamp()).append("',")
                    .append(kline.getVolume()).append(",'").append(kline.getReserve()).append("')");
            if (i < klineList.size() - 1) {
                sql.append(",");
            }
        }
        sql.append(" ON DUPLICATE KEY UPDATE exchange_name='").append(exchangeName).append("'");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.executeUpdate();
    }

    @Override
    public Kline findKlineLatest(String exchangeName, String instrumentId, String granularity) {
        StringBuilder sql = new StringBuilder("select id,`close_price`,exchange_name,high_price,instrument_id,low_price,`open_price`,reserve,volume,date_format(time_stamp,'%Y-%m-%d %T') from ")
                .append("kline_").append(granularity)
                .append(" where exchange_name = '").append(exchangeName).append("' ")
                .append(" and instrument_id = '").append(instrumentId).append("' ")
                .append(" order by time_stamp desc limit 0,1");

        return selectKlineDate(sql);
    }

    @Override
    public Kline findKlineOldest(String exchangeName, String instrumentId, String granularity) {
        StringBuilder sql = new StringBuilder("select id,`close_price`,exchange_name,high_price,instrument_id,low_price,`open_price`,reserve,volume,date_format(time_stamp,'%Y-%m-%d %T') from ")
                .append("kline_").append(granularity)
                .append(" where exchange_name = '").append(exchangeName).append("' ")
                .append(" and instrument_id = '").append(instrumentId).append("' ")
                .append(" order by time_stamp asc limit 0,1");
        return selectKlineDate(sql);
    }

    @Override
    public Kline findKlineLatest(String exchangeName, String instrumentId, String granularity, LocalDateTime time) {
        StringBuilder sql = new StringBuilder("select id,`close_price`,exchange_name,high_price,instrument_id,low_price,`open_price`,reserve,volume,date_format(time_stamp,'%Y-%m-%d %T') from ")
                .append("kline_").append(granularity)
                .append(" where exchange_name = '").append(exchangeName).append("' ")
                .append(" and instrument_id = '").append(instrumentId).append("' ")
                .append(" and time_stamp = '").append(time).append("' ")
                .append(" order by time_stamp desc limit 0,1");

        return selectKlineDate(sql);
    }

    private Kline selectKlineDate(StringBuilder sql) {
        Query query = entityManager.createNativeQuery(sql.toString());
        List<Kline> result = new ArrayList<>();
        List<Object[]> res = query.getResultList();
        for (Object[] item : res) {
            Kline kline = new Kline();
            kline.setClose(new BigDecimal(String.valueOf(item[1])));
            kline.setExchangeName(String.valueOf(item[2]));
            kline.setHigh(new BigDecimal(item[3].toString()));
            kline.setInstrumentId(String.valueOf(item[4]));
            kline.setLow(new BigDecimal(item[5].toString()));
            kline.setOpen(new BigDecimal(item[6].toString()));
            kline.setReserve(JSON.toJSONString(item[7]));
            kline.setVolume(new BigDecimal(item[8].toString()));
            kline.setTimeStamp(DateUtils.getStringByLocalDateTime(String.valueOf(item[9])));
            result.add(kline);
        }
        return result.size() > 0 ? result.get(0) : null;
    }

    @Override
    @Transactional
    public void saveKlineHistory(List<CandleTick> candleTicks, CandleInterval candleInterval, String exchangeName, Market market) {
        List<Kline> klineList = new ArrayList<>();
        candleTicks.forEach(candleTick -> {
            Kline kline = getKlineCandleInterval(candleInterval);
            if (kline != null) {
                kline.setClose(candleTick.getClose());
                kline.setExchangeName(exchangeName);
                kline.setHigh(candleTick.getHigh());
                kline.setInstrumentId(market.getCoinPair().toSymbol());
                kline.setLow(candleTick.getLow());
                kline.setOpen(candleTick.getOpen());
                kline.setVolume(candleTick.getVolume());
                kline.setTimeStamp(candleTick.getTimeStamp());
                kline.setReserve(JSON.toJSONString(candleTick.getReserveMap()));
                klineList.add(kline);
            }
        });
        log.info("{}的{}币对的{}K线数据开始保存到数据库中, 数据量: {}", exchangeName, market.getCoinPair().toSymbol(), candleInterval.getDescribe(), klineList.size());
        if (klineList.size() > 0) {
            saveAll(klineList, candleInterval.getDescribe(), exchangeName);
        }
    }

    private final TreeMap<CandleInterval, TreeMap<LocalDateTime, CandleTick>> candleTickMap = new TreeMap<>();

    @Override
    @Transactional
    public void saveCandleTick(CandleTick candleTick, CandleInterval candleInterval, String exchangeName, Market market) {
        boolean flag = true;
        CandleTick newCandleTick = null;
        if (candleTickMap.size() > 0 && candleTickMap.containsKey(candleInterval)) {
            if (!candleTickMap.get(candleInterval).containsKey(DateUtils.getStringByLocalDateTime(candleTick.getTimeStamp()))) {
                newCandleTick = candleTickMap.get(candleInterval).lastEntry().getValue();
            }
        } else {
            flag = false;
        }
        TreeMap<LocalDateTime, CandleTick> tickTreeMap = new TreeMap<>();
        tickTreeMap.put(DateUtils.getStringByLocalDateTime(candleTick.getTimeStamp()), candleTick);
        candleTickMap.put(candleInterval, tickTreeMap);

        if (flag) {
            Kline kline = getKlineCandleInterval(candleInterval);
            if (kline != null && newCandleTick != null) {
                kline.setClose(newCandleTick.getClose());
                kline.setExchangeName(exchangeName);
                kline.setHigh(newCandleTick.getHigh());
                kline.setInstrumentId(market.getCoinPair().toSymbol());
                kline.setLow(newCandleTick.getLow());
                kline.setOpen(newCandleTick.getOpen());
                kline.setVolume(newCandleTick.getVolume());
                kline.setTimeStamp(newCandleTick.getTimeStamp());
                kline.setReserve(JSON.toJSONString(newCandleTick.getReserveMap()));
                save(kline, candleInterval.getDescribe());
                candleTickMap.clear();
            }
        }
    }

    /**
     * 根据周期实例K线实体
     *
     * @param candleInterval 周期
     * @return K线实体
     */
    private Kline getKlineCandleInterval(CandleInterval candleInterval) {
        Kline kline = null;
        switch (candleInterval) {
            case MINUTES_1:
            case MINUTES_1MIN:
                kline = new Kline1min();
                break;
            case MINUTES_3:
                kline = new Kline3min();
                break;
            case MINUTES_5:
            case MINUTES_5MIN:
                kline = new Kline5min();
                break;
            case MINUTES_15:
            case MINUTES_15MIN:
                kline = new Kline15min();
                break;
            case MINUTES_30:
            case MINUTES_30MIN:
                kline = new Kline30min();
                break;
            case HOUR_1:
            case MINUTES_60:
                kline = new Kline1h();
                break;
            case HOUR_2:
                kline = new Kline2h();
                break;
            case HOUR_3:
                kline = new Kline3h();
                break;
            case HOUR_4:
            case HOUR_4HOUR:
                kline = new Kline4h();
                break;
            case HOUR_6:
                kline = new Kline6h();
                break;
            case HOUR_8:
                kline = new Kline8h();
                break;
            case HOUR_12:
                kline = new Kline12h();
                break;
            case DAY_1:
            case DAY_1DAY:
            case DAY_1_D:
                kline = new Kline1d();
                break;
            case DAY_3:
                kline = new Kline3d();
                break;
            case DAY_7:
            case DAY_7_D:
            case WEEK_1:
            case WEEK_1_1WEEK:
                kline = new Kline7d();
                break;
            case DAY_14:
                kline = new Kline14d();
                break;
            case MONTH_1:
            case MONTH_1_M:
            case MONTH_1MON:
                kline = new Kline1mon();
                break;
            case MONTH_3:
                kline = new Kline3mon();
                break;
            case MONTH_6:
                kline = new Kline6mon();
                break;
            case YEAR_1:
            case YEAR_1_YEAR:
                kline = new Kline1year();
                break;
            default:
                break;
        }
        return kline;
    }
}
