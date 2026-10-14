package com.gautam.concurra.config;

import com.gautam.concurra.model.Product;
import com.gautam.concurra.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner loadData(ProductRepository productRepository) {
        return args -> {
            // Check if data already exists to avoid duplicates on restart
            if (productRepository.count() == 0) {
                // Create Product with ID 1, Name "iPhone 16", Stock 100
                Product iphone = new Product(1L, "iPhone 16", 100);
                productRepository.save(iphone);
                System.out.println("✅ Database Seeded: iPhone 16 (Stock: 100) created with ID: 1");
            }
        };
    }
}
