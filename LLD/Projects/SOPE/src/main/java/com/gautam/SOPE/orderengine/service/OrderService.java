package com.gautam.SOPE.orderengine.service;

import com.gautam.SOPE.orderengine.event.OrderPlacedEvent; // Import this
import com.gautam.SOPE.orderengine.exception.OrderNotFoundException;
import com.gautam.SOPE.orderengine.model.Order;
import org.springframework.context.ApplicationEventPublisher; // Import this
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {

    private final Map<Long, Order> orderDatabase = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // Spring's Event Broadcaster
    private final ApplicationEventPublisher eventPublisher;

    public OrderService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public Long createOrder() {
        long id = idGenerator.getAndIncrement();
        Order order = new Order();
        orderDatabase.put(id, order);

        // --- THE CHANGE IS HERE ---
        // We publish the event. The service doesn't know WHO is listening.
        // It just says "Job done, here is the event."
        System.out.println("1. Service: Order saved to database.");
        eventPublisher.publishEvent(new OrderPlacedEvent(this, order));

        return id;
    }

    // ... (keep the other methods like advanceOrderStatus same as before) ...
    public String advanceOrderStatus(Long orderId) {
        Order order = orderDatabase.get(orderId);
        if (order == null) {
            // Change this line:
            throw new OrderNotFoundException("Order with ID " + orderId + " does not exist.");
        }
        order.nextState();
        return order.getState().getClass().getSimpleName();
    }

    public String getStatus(Long orderId) {
        Order order = orderDatabase.get(orderId);
        return order != null ? order.getState().getClass().getSimpleName() : "Not Found";
    }
}
