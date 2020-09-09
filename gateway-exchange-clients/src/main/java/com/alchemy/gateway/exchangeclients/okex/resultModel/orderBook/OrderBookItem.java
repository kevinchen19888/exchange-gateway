package com.alchemy.gateway.exchangeclients.okex.resultModel.orderBook;

public interface OrderBookItem<T> {
    String getPrice();

    T getSize();
}
