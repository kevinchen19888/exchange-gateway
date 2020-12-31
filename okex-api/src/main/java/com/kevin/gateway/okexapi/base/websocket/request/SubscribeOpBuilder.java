package com.kevin.gateway.okexapi.base.websocket.request;

import com.kevin.gateway.core.Symbolic;
import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订阅指令构建器
 */
public class SubscribeOpBuilder implements OperationBuilder {
    @Getter
    private final List<SubscriptionTopic> subscriptionTopics = new ArrayList<>();

    public SubscribeOpBuilder() {
    }

    public SubscribeOpBuilder addSubscriptionTopic(ChannelTrait channel, @Nullable Symbolic filter) {
        SubscriptionTopic result = SubscriptionTopic.of(channel, filter);
        subscriptionTopics.add(result);
        return this;
    }

    public List<SubscriptionTopic> getSubscriptionTopics() {
        return subscriptionTopics;
    }

    @Override
    public Operation build() {
        return new Operation() {
            @Override
            public String getOp() {
                return Op.SUBSCRIBE.getValue();
            }

            @Override
            public List<String> getArgs() {
                return subscriptionTopics.stream().map(SubscriptionTopic::toArgString).collect(Collectors.toList());
            }
        };
    }
}
