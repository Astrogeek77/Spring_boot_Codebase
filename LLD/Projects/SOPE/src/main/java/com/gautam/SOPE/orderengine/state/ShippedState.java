package com.gautam.SOPE.orderengine.state;

import com.gautam.SOPE.orderengine.model.Order;

public class ShippedState implements OrderState {
    @Override
    public void next(Order order) {
        System.out.println("Order is already shipped. No further steps.");
    }

    @Override
    public void printStatus() {
        System.out.println("Order is currently in [SHIPPED] state.");
    }
}
