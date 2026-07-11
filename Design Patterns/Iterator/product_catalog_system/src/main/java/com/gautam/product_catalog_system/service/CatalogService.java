package com.gautam.product_catalog_system.service;


import com.gautam.product_catalog_system.model.Product;
import com.gautam.product_catalog_system.pattern.ProductCatalog;
import com.gautam.product_catalog_system.pattern.ProductIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService {

    private final ProductCatalog catalog;

    @Autowired
    public CatalogService(ProductCatalog catalog) {
        this.catalog = catalog;
    }

    public List<Product> getAllProductsViaIterator() {
        List<Product> result = new ArrayList<>();

        // The Client uses the Iterator! No need for for-loops based on indices or internal structures.
        ProductIterator iterator = catalog.createIterator();

        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        return result;
    }
}
