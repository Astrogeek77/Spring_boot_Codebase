package com.gautam.SOPE.orderengine.model;

import com.gautam.SOPE.orderengine.state.OrderState;
import com.gautam.SOPE.orderengine.state.NewState;

public class Order {

    private OrderState state;

    // Default constructor sets initial state
    public Order() {
        this.state = new NewState();
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public OrderState getState() {
        return state;
    }

    // The "Request" methods that delegate to the current state
    public void nextState() {
        state.next(this);
    }

    public void printStatus() {
        state.printStatus();
    }
}
