package com.kevin.gateway.strategy;

import com.kevin.gateway.core.Coin;
import com.kevin.gateway.core.CoinPair;
import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.okexapi.base.util.OkexEnvironment;
import com.kevin.gateway.okexapi.base.util.OrderSide;
import com.kevin.gateway.okexapi.spot.SpotApi;
import com.kevin.gateway.okexapi.spot.SpotApiImpl;
import com.kevin.gateway.okexapi.spot.request.SpotPlaceOrderRequest;
import com.kevin.gateway.okexapi.spot.response.SpotPlaceOrderResponse;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

/**
 * okex 打新币买单
 * 配置&运行步骤
 * 1,配置环境变量:API_KEY,SECRET_KEY
 * 2,引入 okhttp3 & spring-boot-starter-json 两个依赖
 * 3,配置 打新开始时间/账户id/订单类型/打新币对/购买金额...(所有参数参考说明)
 * 4,配置完成后运行程序(程序修改为一直进行下单,直到账户资产不足抛出异常)
 */
public class OkexNewCoinTrade {

    @SneakyThrows
    public static void main(String[] args) {
        // 设置的开始时间比交易所公告的打新时间早2s
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 59, 58));
        System.out.println("okex打新开始时间:" + startTime.format(FORMATTER));

        SpotPlaceOrderRequest.SpotPlaceLimitOrderRequest request = new SpotPlaceOrderRequest.SpotPlaceLimitOrderRequest();
        request.setInstrumentId(CoinPair.of(Coin.of("OKB"), Coin.of("USDT")));
        request.setSide(OrderSide.BUY);
        request.setPrice(BigDecimal.valueOf(10));
        request.setSize(BigDecimal.valueOf(1));

        // 运行打新程序 todo
        while (true) {
            if (LocalDateTime.now().isAfter(startTime)) {
                SpotPlaceOrderResponse resp = okexSpotApi.addOrder(credentials, request);
                if ("0".equals(resp.getErrorCode())) {
                    System.out.println("下单成功,结束执行");
                    return;
                }
                System.out.println(String.format("下单失败,结束执行,errCode:%s,errMsg:%s", resp.getErrorCode(), resp.getErrorMessage()));
                return;
            } else {
                System.out.println("未到打新时间,休眠1s,现在时间:" + LocalDateTime.now().format(FORMATTER));
                TimeUnit.SECONDS.sleep(1);
            }
        }

    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss");
    private static final SpotApi okexSpotApi = new SpotApiImpl(OkexEnvironment.PRODUCT);
    private static Credentials credentials;

    static {
        String apiKey = System.getenv("API_KEY");
        String secretKey = System.getenv("SECRET_KEY");
        String passPhrase = System.getenv("PASS_PHRASE");
        if (!StringUtils.hasText(apiKey) || !StringUtils.hasText(secretKey) || !StringUtils.hasText(passPhrase)) {
            throw new IllegalArgumentException("apiKey/secretKey/passPhrase未配置环境变量");
        }
        credentials = Credentials.of(apiKey, secretKey, passPhrase);
    }
}
