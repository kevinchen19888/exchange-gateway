package com.kevin.gateway.huobiapi.base.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class UrlParamsBuilder {
    @Data
    class ParamsMap {

        final Map<String, String> postParamsMap = new HashMap<>();
        final Map<String, List> stringListMap = new HashMap<>();

        void put(String name, String value) {

            if (name == null || "".equals(name)) {
                throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
                        "[URL] Key can not be null");
            }
            if (value == null || "".equals(value)) {
                return;
            }

            postParamsMap.put(name, value);
        }

        void put(String name, Integer value) {
            put(name, value != null ? Integer.toString(value) : null);
        }

        void put(String name, Date value, String format) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
            put(name, value != null ? dateFormatter.format(value) : null);
        }

        void put(String name, Long value) {
            put(name, value != null ? Long.toString(value) : null);
        }

        void put(String name, BigDecimal value) {
            put(name, value != null ? value.toPlainString() : null);
        }

        Map<String, String> getPostParamsMap() {
            return postParamsMap;
        }
    }

    //private static final MediaType JSON_TYPE = MediaType.parse("application/json");
    private final ParamsMap paramsMap = new ParamsMap();
    private final ParamsMap postBodyMap = new ParamsMap();
    private ArrayList paramList = new ArrayList();
    private boolean postMode = false;
    private boolean arrayMode = false;
    private ObjectMapper objectMapper = new ObjectMapper();

    public static UrlParamsBuilder build() {
        return new UrlParamsBuilder();
    }

    private UrlParamsBuilder() {
    }

    @SneakyThrows
    public String getPostParamsJson() {
        Map<String, String> postParamsMap = postBodyMap.getPostParamsMap();
        return new ObjectMapper().writeValueAsString(postParamsMap);
    }

    public UrlParamsBuilder setArrayMode(boolean mode) {
        arrayMode = mode;
        return this;
    }

    public UrlParamsBuilder setPostMode(boolean mode) {
        postMode = mode;
        return this;
    }

    public <T extends Enum> UrlParamsBuilder putToUrl(String name, T value) {
        if (value != null) {
            paramsMap.put(name, value.toString());
        }
        return this;
    }

    public UrlParamsBuilder putToUrl(String name, String value) {
        paramsMap.put(name, value);
        return this;
    }

    public UrlParamsBuilder putToUrl(String name, Date value, String format) {
        paramsMap.put(name, value, format);
        return this;
    }

    public UrlParamsBuilder putToUrl(String name, Integer value) {
        paramsMap.put(name, value);
        return this;
    }

    public UrlParamsBuilder putToUrl(String name, Long value) {
        paramsMap.put(name, value);
        return this;
    }

    public UrlParamsBuilder putToUrl(String name, BigDecimal value) {
        paramsMap.put(name, value);
        return this;
    }

    public UrlParamsBuilder putToPost(String name, String value) {
        postBodyMap.put(name, value);
        return this;
    }

    public <T extends Enum> UrlParamsBuilder putToPost(String name, T value) {
        if (value != null) {
            postBodyMap.put(name, value.toString());
        }
        return this;
    }

    public UrlParamsBuilder putToPost(String name, Date value, String format) {
        postBodyMap.put(name, value, format);
        return this;
    }

    public UrlParamsBuilder putToPost(String name, Integer value) {
        postBodyMap.put(name, value);
        return this;
    }

    public <T> UrlParamsBuilder putToPost(String name, List list) {
        postBodyMap.stringListMap.put(name, list);
        return this;
    }

    public UrlParamsBuilder putToPost(String name, Long value) {
        postBodyMap.put(name, value);
        return this;
    }

    public UrlParamsBuilder putToPost(String name, BigDecimal value) {
        postBodyMap.put(name, value);
        return this;
    }

    public <T> UrlParamsBuilder putToList(T t) {
        paramList.add(t);
        return this;
    }

    public String buildUrl() {
        Map<String, String> map = new HashMap<>(paramsMap.postParamsMap);
        StringBuilder head = new StringBuilder("?");
        return AppendUrl(map, head);

    }

    public String buildSignature() {
        Map<String, String> map = new TreeMap<>(paramsMap.postParamsMap);
        StringBuilder head = new StringBuilder();
        return AppendUrl(map, head);

    }

    private String AppendUrl(Map<String, String> map, StringBuilder stringBuilder) {
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (!("").equals(stringBuilder.toString())) {
                stringBuilder.append("&");
            }
            stringBuilder.append(entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append(urlEncode(entry.getValue()));
        }
        return stringBuilder.toString();
    }

    /*@SneakyThrows
    public RequestBody buildPostBody() {

        // 判断是否为数组模式
        if (this.arrayMode) {
            return RequestBody.create(JSON_TYPE, objectMapper.writeValueAsString(this.paramList));
        }

        if (postBodyMap.stringListMap.isEmpty()) {
            if (postBodyMap.postParamsMap.isEmpty()) {
                return RequestBody.create(JSON_TYPE, "");
            } else {
                return RequestBody.create(JSON_TYPE, objectMapper.writeValueAsString(postBodyMap.postParamsMap));
            }
        } else {
            log.info("====>" + objectMapper.writeValueAsString(postBodyMap.stringListMap));
            return RequestBody.create(JSON_TYPE, objectMapper.writeValueAsString(postBodyMap.stringListMap));
        }
    }*/

    public boolean hasPostParam() {
        return !postBodyMap.postParamsMap.isEmpty() || postMode;
    }


    @SneakyThrows
    public String buildUrlToJsonString() {
        return objectMapper.writeValueAsString(paramsMap.postParamsMap);
    }

    /**
     * 使用标准URL Encode编码。注意和JDK默认的不同，空格被编码为%20而不是+。
     *
     * @param s String字符串
     * @return URL编码后的字符串
     */
    private static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new HuobiApiException(HuobiApiException.RUNTIME_ERROR,
                    "[URL] UTF-8 encoding not supported!");
        }
    }

    public UrlParamsBuilder putToUrl(String urlParam) {
        String[] items = urlParam.split("&");
        for (String item : items) {
            String[] split = item.split("=");
            paramsMap.put(split[0], split[1]);
        }
        return this;
    }
}
