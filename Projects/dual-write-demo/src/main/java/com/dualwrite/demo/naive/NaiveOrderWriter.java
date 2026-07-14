package com.dualwrite.demo.naive;

import com.dualwrite.demo.common.Order;
import com.dualwrite.demo.common.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Deliberately a separate bean from NaiveOrderService.
 *
 * Spring's @Transactional works via a proxy wrapping the bean; calling an
 * @Transactional method on `this` from within the same class (self-
 * invocation) bypasses that proxy entirely, so REQUIRES_NEW would silently
 * do nothing if this method lived in NaiveOrderService itself. Putting it
 * in its own bean and calling it through DI ensures the proxy -- and
 * therefore the independent commit -- is real. This mirrors a genuinely
 * common way the dual write bug creeps into real codebases: a repository/
 * DAO layer object with its own transaction boundary, called from a
 * service that also happens to talk to a message broker.
 */
@Component
@RequiredArgsConstructor
public class NaiveOrderWriter {

    private final OrderRepository orderRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
