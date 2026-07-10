package com.gautam.dynamic_pricing_engine.decorator;


import com.gautam.dynamic_pricing_engine.component.PriceComponent;

public abstract class PriceDecorator implements PriceComponent {
    protected PriceComponent wrappedComponent;

    public PriceDecorator(PriceComponent wrappedComponent) {
        this.wrappedComponent = wrappedComponent;
    }

    @Override
    public double calculatePrice() {
        // Delegates to the wrapped object
        return wrappedComponent.calculatePrice();
    }
}