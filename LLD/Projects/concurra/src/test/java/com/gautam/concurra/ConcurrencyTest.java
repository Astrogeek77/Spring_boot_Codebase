package com.gautam.concurra;

import com.gautam.concurra.model.Product;
import com.gautam.concurra.repository.ProductRepository;
import com.gautam.concurra.service.FlashSaleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ConcurrencyTest {

    @Autowired
    private FlashSaleService flashSaleService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void testRaceCondition() throws InterruptedException {
        // 1. Setup: Create a product with ONLY 10 items in stock
        Product iphone = new Product(1L, "iPhone 16", 10);
        productRepository.save(iphone);

        // 2. Simulate 100 users trying to buy at the exact same time
        int numberOfThreads = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            executorService.execute(() -> {
                try {
                    flashSaleService.purchase(1L);
                } catch (Exception e) {
                    // Ignore "Sold out" exceptions
                }
            });
        }

        // 3. Wait for all threads to finish
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);

        // 4. Verify the result
        Product updatedProduct = productRepository.findById(1L).orElseThrow();

        // IF THE CODE IS CORRECT: Stock should be 0.
        // IF THE CODE IS BROKEN: Stock might be negative, or we printed "Purchase successful" more than 10 times.

        System.out.println("Final Stock in DB: " + updatedProduct.getStock());

        // This assertion will likely FAIL or show weird behavior in logs
        // ideally stock should stop at 0.
    }
}
