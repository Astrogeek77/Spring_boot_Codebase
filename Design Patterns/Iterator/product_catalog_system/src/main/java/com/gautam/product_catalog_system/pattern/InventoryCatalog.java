package com.gautam.product_catalog_system.pattern;


import com.gautam.product_catalog_system.model.Product;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class InventoryCatalog implements ProductCatalog {

    // The internal representation is hidden from the client
    private List<Product> products;

    public InventoryCatalog() {
        this.products = new ArrayList<>();
        // Pre-populating some dummy data for demonstration
        products.add(new Product("P01", "Mechanical Keyboard", 120.00));
        products.add(new Product("P02", "Wireless Mouse", 50.00));
        products.add(new Product("P03", "Curved Monitor", 350.00));
        products.add(new Product("P04", "USB-C Hub", 30.00));
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    @Override
    public ProductIterator createIterator() {
        return new InventoryIterator();
    }

    // Private inner class: It knows exactly how to traverse the List
    private class InventoryIterator implements ProductIterator {
        private int currentPosition = 0;

        @Override
        public boolean hasNext() {
            return currentPosition < products.size();
        }

        @Override
        public Product next() {
            if (!hasNext()) {
                return null;
            }
            Product product = products.get(currentPosition);
            currentPosition++;
            return product;
        }
    }
}
