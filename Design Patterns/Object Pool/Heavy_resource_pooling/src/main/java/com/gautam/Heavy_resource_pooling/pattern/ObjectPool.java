package com.gautam.Heavy_resource_pooling.pattern;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ObjectPool<T> {
    private final Queue<T> pool;
    private final int maxPoolSize;
    private final AtomicInteger currentSize;

    public ObjectPool(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        this.pool = new ConcurrentLinkedQueue<>();
        this.currentSize = new AtomicInteger(0);
    }

    // Subclasses must implement how the specific object is created
    protected abstract T create();

    public T borrowObject() {
        T object = pool.poll(); // Try to get an existing object from the queue

        if (object != null) {
            return object;
        }

        // If pool is empty, but we haven't reached the max size, create a new one
        if (currentSize.get() < maxPoolSize) {
            currentSize.incrementAndGet();
            return create();
        }

        // In a production system, you might block/wait here instead of throwing an exception
        throw new RuntimeException("Pool exhausted. Maximum capacity of " + maxPoolSize + " reached. Please try again later.");
    }

    public void returnObject(T object) {
        if (object != null) {
            // Reset the object's state here if necessary before returning it to the pool
            pool.offer(object);
        }
    }

    public int getAvailableCount() {
        return pool.size();
    }

    public int getTotalCreated() {
        return currentSize.get();
    }
}
