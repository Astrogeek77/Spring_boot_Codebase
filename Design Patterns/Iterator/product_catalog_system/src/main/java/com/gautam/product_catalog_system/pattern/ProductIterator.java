package com.gautam.product_catalog_system.pattern;

import com.gautam.product_catalog_system.model.Product;

// 1. The Iterator Interface
public interface ProductIterator {
    boolean hasNext();
    Product next();
}


