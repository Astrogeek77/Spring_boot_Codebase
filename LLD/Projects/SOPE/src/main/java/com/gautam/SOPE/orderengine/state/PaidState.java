package com.gautam.SOPE.orderengine.state;

import com.gautam.SOPE.orderengine.model.Order;

public class PaidState implements OrderState {
    @Override
    public void next(Order order) {
        System.out.println("Order has been shipped!");
        order.setState(new ShippedState());
    }

    @Override
    public void printStatus() {
        System.out.println("Order is currently in [PAID] state. Preparing for shipment.");
    }
}
