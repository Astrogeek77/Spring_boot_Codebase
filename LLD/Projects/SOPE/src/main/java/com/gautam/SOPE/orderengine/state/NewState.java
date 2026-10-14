package com.gautam.SOPE.orderengine.state;

import com.gautam.SOPE.orderengine.model.Order;

public class NewState implements OrderState {
    @Override
    public void next(Order order) {
        System.out.println("Order payment processed successfully.");
        order.setState(new PaidState());
    }

    @Override
    public void printStatus() {
        System.out.println("Order is currently in [NEW] state. Waiting for payment.");
    }
}
