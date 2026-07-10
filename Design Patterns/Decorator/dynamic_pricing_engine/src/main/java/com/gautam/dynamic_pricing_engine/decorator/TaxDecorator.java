package com.gautam.dynamic_pricing_engine.decorator;

import com.gautam.dynamic_pricing_engine.component.PriceComponent;

public class TaxDecorator extends PriceDecorator {
    private double taxRate = 0.10; // 10% tax

    public TaxDecorator(PriceComponent wrappedComponent) {

        super(wrappedComponent);
    }

    @Override
    public double calculatePrice() {
        double basePrice = super.calculatePrice();
        return basePrice + (basePrice * taxRate);
    }
}
