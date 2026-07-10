package com.gautam.dynamic_pricing_engine.decorator;

import com.gautam.dynamic_pricing_engine.component.PriceComponent;

public class PackagingDecorator extends PriceDecorator {
    private double packagingFee = 5.00; // Flat $5 fee

    public PackagingDecorator(PriceComponent wrappedComponent) {
        super(wrappedComponent);
    }

    @Override
    public double calculatePrice() {
        return super.calculatePrice() + packagingFee;
    }
}
