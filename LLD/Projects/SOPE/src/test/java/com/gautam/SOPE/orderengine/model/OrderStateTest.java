package com.gautam.SOPE.orderengine.model;

import com.gautam.SOPE.orderengine.state.NewState;
import com.gautam.SOPE.orderengine.state.PaidState;
import com.gautam.SOPE.orderengine.state.ShippedState;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderStateTest {

    @Test
    void testOrderLifecycleFlow() {
        // 1. Setup - Create the object
        Order order = new Order();

        // 2. Assert Initial State
        // explicitly check the class type to verify the state
        assertTrue(order.getState() instanceof NewState, "Order should start in NEW state");

        // 3. Act - Transition to next state
        order.nextState();

        // 4. Assert - Verify transition to PAID
        assertTrue(order.getState() instanceof PaidState, "Order should transition to PAID state after first next()");

        // 5. Act - Transition to next state
        order.nextState();

        // 6. Assert - Verify transition to SHIPPED
        assertTrue(order.getState() instanceof ShippedState, "Order should transition to SHIPPED state");
    }

    @Test
    void testShippedStateIsFinal() {
        // Setup an order manually in the SHIPPED state to save time
        Order order = new Order();
        order.setState(new ShippedState());

        // Act - Try to move past the final state
        order.nextState();

        // Assert - Should still be SHIPPED (idempotency)
        assertTrue(order.getState() instanceof ShippedState, "Order should remain in SHIPPED state");
    }
}
