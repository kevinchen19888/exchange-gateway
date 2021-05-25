package com.kevin.gateway.strategy;

import com.kevin.gateway.core.Credentials;
import com.kevin.gateway.huobiapi.base.HuobiEnvironment;
import com.kevin.gateway.huobiapi.spot.SpotApi;
import com.kevin.gateway.huobiapi.spot.SpotApiImpl;
import com.kevin.gateway.huobiapi.spot.model.SpotOrdersPlaceType;
import com.kevin.gateway.huobiapi.spot.request.OrderRequest;
import com.kevin.gateway.huobiapi.spot.response.SpotOrderResponse;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import okhttp3.*;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * huobi 打新币买单
 * 配置&运行步骤
 * 1,配置环境变量:API_KEY,SECRET_KEY
 * 2,引入 okhttp3 & spring-boot-starter-json 两个依赖
 * 3,配置 打新开始时间/账户id/订单类型/打新币对/购买金额...(所有参数参考说明)
 * 4,配置完成后运行程序(程序修改为一直进行下单,直到账户资产不足抛出异常)
 * <p>
 * 备注::
 * 账户id: 可以在登录web端后进入调试界面(按F12)通过点击:'订单'菜单下的任意一个子菜单(如币币&杠杆订单)
 * 获取返回后的响应结果,然后通过调试工具中的 search工具搜索关键字:account-id 获取;
 */
public class HuobiNewCoinTrade {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd:HH:mm:ss");
    private static final SpotApi huobiApi = new SpotApiImpl(HuobiEnvironment.PRODUCT);
    private static final String USDT = "usdt";
    private static Credentials credentials;

    @SneakyThrows
    public static void main(String[] args) {
        // 设置的开始时间比交易所公告的打新时间早2s
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 29, 58));
        System.out.println("huobi打新开始时间:" + startTime.format(FORMATTER));

        // 下单参数组装开始 ==============================
        OrderRequest request = OrderRequest.builder()
                .symbol("ht" + USDT)
                .type(SpotOrdersPlaceType.BUY_LIMIT)
                .accountId("11400926")
                .amount(BigDecimal.valueOf(1))
                .price(BigDecimal.valueOf(5))
                .build();
        // 下单参数组装完成 ==============================

        order(startTime, request);
    }

    @SuppressWarnings("all")
    private static void order(LocalDateTime startTime, OrderRequest request) throws InterruptedException {
        int count = 0;
        while (true) {
            try {
                if (LocalDateTime.now().isBefore(startTime)) {
                    System.out.println("时间不满足  sleep 1s,现在时间:" + LocalDateTime.now().format(FORMATTER));
                    Thread.sleep(1000);
                    continue;
                }
                // 批量订单下单
                SpotOrderResponse resp = huobiApi.order(credentials, request);
                if ("ok".equals(resp.getStatus())) {
                    count++;
                    System.out.println("已下单,下单次数:" + count);
                } else {
                    String errMsg = String.format("huobi批量下单结果错误:%s,\n执行结束",
                            HuobiEnvironment.PRODUCT.getObjectMapper().writeValueAsString(resp));
                    System.out.println(errMsg);
                    break;
                }
                Thread.sleep(30);
            } catch (Exception e) {
                System.out.println("下单出现异常:" + e);
                break;
            }
        }
    }


    public static boolean isBlankStr(String string) {
        return string == null || "".equals(string);
    }

    static {
        String apiKey = System.getenv("API_KEY");
        String secretKey = System.getenv("SECRET_KEY");
        if (isBlankStr(apiKey) || isBlankStr(secretKey)) {
            throw new IllegalArgumentException("apiKey 或 secretKey未配置环境变量");
        }
        credentials = Credentials.of(apiKey, secretKey, null);
    }
}
