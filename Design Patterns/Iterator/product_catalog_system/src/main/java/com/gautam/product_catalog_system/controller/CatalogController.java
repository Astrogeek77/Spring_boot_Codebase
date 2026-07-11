package com.gautam.product_catalog_system.controller;


import com.gautam.product_catalog_system.model.Product;
import com.gautam.product_catalog_system.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

    private final CatalogService catalogService;

    @Autowired
    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/products")
    public List<Product> fetchAllProducts() {
        // Triggering the traversal via Iterator
        return catalogService.getAllProductsViaIterator();
    }
}
