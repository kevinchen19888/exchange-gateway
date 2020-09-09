package com.alchemy.gateway.quotation.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
public class RoutingKey {

    /**
     * 分隔符
     */
    private String separator;

    /**
     * 组成路由键的一部分
     */
    private List<String> getPart;

    public RoutingKey(String separator) {
        this.separator = separator;
        this.getPart = new ArrayList<>();
    }

    public RoutingKey addPart(@NonNull String part) {
        Pattern p = Pattern.compile("^[a-z0-9A-Z]+$|^([A-Z]+?)/([A-Z]+?)$");
        Matcher matcher = p.matcher(part);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("不符合要求,路由键只能是大小写字母和数字");
        }
        getPart.add(part);
        return this;
    }

    @Override
    public String toString() {
        StringJoiner routingKey = new StringJoiner(separator);
        //将值添加到StringJoiner
        getPart.forEach(routingKey::add);
        return routingKey.toString();
    }
}
