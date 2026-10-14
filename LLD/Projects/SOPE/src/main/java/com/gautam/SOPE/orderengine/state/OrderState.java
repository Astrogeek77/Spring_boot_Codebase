package com.gautam.SOPE.orderengine.state;

import com.gautam.SOPE.orderengine.model.Order;

public interface OrderState {
    void next(Order order);
    void printStatus();
}