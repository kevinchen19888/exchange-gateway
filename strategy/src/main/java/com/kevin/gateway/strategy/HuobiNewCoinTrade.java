package com.kevin.gateway.strategy;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;
import okhttp3.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * huobi 打新币买单
 * 配置&运行步骤
 * 1,配置环境变量:API_KEY,SECRET_KEY
 * 2,引入 okhttp3 & spring-boot-starter-json 两个依赖
 * 3,配置 打新开始时间/账户id/订单类型/打新币对/购买金额...(所有参数参考说明)
 * 4,配置完成后运行程序
 */
public class HuobiNewCoinTrade {

    @SneakyThrows
    public static void main(String[] args) {
        // 设置的开始时间比交易所公告的打新时间早2s
        LocalDateTime startTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 59, 58));
        System.out.println("打新开始时间:" + startTime.format(DateTimeFormatter.ISO_DATE_TIME));

        OrderReq req = OrderReq.builder()
                .accountId("11400926")
                .type(OrderType.BUY_LIMIT)
                .symbol("raiusdt")
                .amount(BigDecimal.valueOf(3))
                .price(BigDecimal.valueOf(1.8))
                .build();

        monitorAndPlaceOrder(startTime, req);
    }

    private static void monitorAndPlaceOrder(LocalDateTime beginTime, OrderReq param) {
        String apiKey = System.getenv("API_KEY");
        String secretKey = System.getenv("SECRET_KEY");
        if (isBlankStr(apiKey) || isBlankStr(secretKey)) {
            throw new IllegalArgumentException("apiKey 或 secretKey未配置环境变量");
        }

        Map<String, Object> paramMap = new HashMap<>();
        while (true) {
            try {
                if (LocalDateTime.now().isAfter(beginTime)) {
                    paramMap.put("account-id", param.getAccountId());//
                    paramMap.put("symbol", param.getSymbol());// raiusdt
                    paramMap.put("type", param.getType().val);
                    paramMap.put("amount", param.getAmount());
                    if (OrderType.BUY_LIMIT == param.getType()) {
                        if (param.getPrice() == null) {
                            throw new IllegalArgumentException("限价单必须设置价格");
                        }
                        paramMap.put("price", param.getPrice());
                    }

                    String uri = "https://api.huobipro.com/v1/order/orders/place";
                    Map<String, Object> requestMap = createSignature(apiKey, secretKey, "POST", uri, paramMap);

                    RequestBody body = RequestBody.create(JSON_TYPE, MAPPER.writeValueAsString(requestMap));
                    Request.Builder builder = new Request.Builder().url(uri + "?" + toQueryString(requestMap)).post(body);
                    Request request = builder.build();
                    Response response = httpClient.newCall(request).execute();

                    String resp = response.body().string();
                    System.out.println("响应结果:" + resp);
                    // 下单成功则不再执行程序
                    OrderResp respObj = MAPPER.readValue(resp, OrderResp.class);
                    if ("ok".equals(respObj.getStatus())) {
                        System.out.println("成功下单,结束执行===========>");
                        break;
                    }
                    Thread.sleep(100);
                } else {
                    System.out.println("时间不满足  sleep 1s");
                    Thread.sleep(1000);
                }
            } catch (Exception ex) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("出现异常,程序退出:" + ex);
                break;
            }
        }
    }

    @Data
    private static class OrderResp {
        private String status;
        private String data;
        @JsonAlias("err-code")
        private String errCode;
        @JsonAlias("err-msg")
        private String errMsg;
    }
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final OkHttpClient httpClient = new OkHttpClient();

    public static Map<String, Object> createSignature(String appKey, String appSecretKey, String method, String uri,
                                                      Map<String, Object> paramMap) {
        StringBuilder sb = new StringBuilder(1024);
        int index = uri.indexOf("//");
        String subString = uri.substring(index + 2);
        int index2 = subString.indexOf("/");
        String host = subString.substring(0, index2);
        String constant = subString.substring(index2);
        sb.append(method.toUpperCase()).append('\n') // GET
                .append(host.toLowerCase()).append('\n') // Host
                .append(constant).append('\n'); // /path
        paramMap.remove("Signature");
        paramMap.put("AccessKeyId", appKey);
        paramMap.put("SignatureVersion", "2");
        paramMap.put("SignatureMethod", "HmacSHA256");
        paramMap.put("Timestamp", Instant.ofEpochSecond(Instant.now().getEpochSecond()).atZone(ZONE_GMT).format(DT_FORMAT));
        // build signature:
        SortedMap<String, Object> map = new TreeMap<>(paramMap);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().toString();
            sb.append(key).append('=').append(urlEncode(value)).append('&');
        }
        // remove last '&':
        sb.deleteCharAt(sb.length() - 1);
        // sign:
        Mac hmacSha256;
        try {
            hmacSha256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec secKey = new SecretKeySpec(appSecretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSha256.init(secKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such algorithm: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Invalid key: " + e.getMessage());
        }
        String payload = sb.toString();
        byte[] hash = hmacSha256.doFinal(payload.getBytes(StandardCharsets.UTF_8));
        String actualSign = Base64.getEncoder().encodeToString(hash);
        paramMap.put("Signature", actualSign);
        return paramMap;
    }

    public static boolean isBlankStr(String string) {
        return string == null || "".equals(string);
    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("UTF-8 encoding not supported!");
        }
    }

    private static String toQueryString(Map<String, Object> params) {
        return String.join("&",
                params.entrySet().stream()
                        .map((entry) -> entry.getKey() + "=" + urlEncode(entry.getValue().toString()))
                        .collect(Collectors.toList()));
    }

    private static final DateTimeFormatter DT_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss");

    private static final ZoneId ZONE_GMT = ZoneId.of("Z");
    private static final MediaType JSON_TYPE = MediaType.parse("application/json");

    @Data
    @Builder
    private static class OrderReq {
        /**
         * 账户id:可以在登录web端后进入调试界面(按F12)通过点击:'订单'菜单下的任意一个子菜单(如币币&杠杆订单)
         * 获取返回后的响应结果,然后通过调试工具中的 search工具搜索关键字:account-id 获取;
         */
        private String accountId;
        /**
         * 要购买的打新币对,如要购买RAI,则值应为:raiusdt
         */
        private String symbol;
        /**
         * 订单交易量（市价买单为订单交易额）
         * 限价单为
         */
        private BigDecimal amount;
        /**
         * 订单价格（对市价单无效）
         */
        private BigDecimal price;
        private OrderType type;
    }

    public enum OrderType {
        /**
         * 市价买入
         */
        BUY_MARKET("buy-market"),
        /**
         * 限价买入
         */
        BUY_LIMIT("buy-limit");

        private final String val;

        OrderType(String s) {
            this.val = s;
        }

        public String getVal() {
            return val;
        }
    }

}
