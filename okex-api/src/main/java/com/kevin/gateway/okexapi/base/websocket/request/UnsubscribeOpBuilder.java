package com.kevin.gateway.okexapi.base.websocket.request;

import com.kevin.gateway.core.Symbolic;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 取消订阅构建器
 */
public class UnsubscribeOpBuilder implements OperationBuilder {
    private final List<SubscriptionTopic> subscriptionTopics;

    public UnsubscribeOpBuilder() {
        subscriptionTopics = new ArrayList<>();
    }
    public UnsubscribeOpBuilder(List<SubscriptionTopic> subscriptionTopics) {
        this.subscriptionTopics = subscriptionTopics;
    }

    public UnsubscribeOpBuilder addSubscriptionTopic(ChannelTrait channel, @Nullable Symbolic filter) {
        SubscriptionTopic result = SubscriptionTopic.of(channel, filter);
        subscriptionTopics.add(result);
        return this;
    }

    @Override
    public Operation build() {
        return new Operation() {
            @Override
            public String getOp() {
                return Op.UNSUBSCRIBE.getValue();
            }

            @Override
            public List<String> getArgs() {
                return subscriptionTopics.stream()
                        .map(SubscriptionTopic::toArgString)
                        .collect(Collectors.toList());
            }
        };
    }
}
