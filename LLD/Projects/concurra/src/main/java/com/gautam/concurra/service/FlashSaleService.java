package com.gautam.concurra.service;

import com.gautam.concurra.model.Product;
import com.gautam.concurra.repository.ProductRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlashSaleService {

    private final ProductRepository productRepository;

    public FlashSaleService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Cacheable(value = "products", key = "#productId")
    public Product getProductDetails(Long productId) {
        System.out.println("Fetching from Database..."); // Debug log to prove cache miss
        return productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // THIS METHOD IS NOT THREAD SAFE
    @Transactional
    @CacheEvict(value="products", key="#productId")
    public void purchase(Long productId) {
        Product product = productRepository.findByIdWithLock(productId).orElseThrow(() -> new RuntimeException("Product Not Found!"));

        if (product.getStock() > 0) {
            // Simulate a tiny processing delay (e.g., credit card check)
            // This widens the "race window" to make the error more likely to happen
            try { Thread.sleep(50); } catch (InterruptedException e) {}

            product.setStock(product.getStock() - 1);
            productRepository.save(product);
            System.out.println("Purchase successful! Stock left: " + product.getStock());
        } else {
            System.out.println("Sold out!");
            throw new RuntimeException("Sold out");
        }
    }
}
