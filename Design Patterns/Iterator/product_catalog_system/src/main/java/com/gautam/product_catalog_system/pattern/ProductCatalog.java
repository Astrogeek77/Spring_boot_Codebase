package com.gautam.product_catalog_system.pattern;

// 2. The Aggregate/Collection Interface
public interface ProductCatalog {
    ProductIterator createIterator();
}
