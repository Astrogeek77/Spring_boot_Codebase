package com.gautam.dynamic_pricing_engine.component;

public class BasePrice implements PriceComponent {
    private double price;

    public BasePrice(double price) {
        this.price = price;
    }

    @Override
    public double calculatePrice() {
        return this.price;
    }
}