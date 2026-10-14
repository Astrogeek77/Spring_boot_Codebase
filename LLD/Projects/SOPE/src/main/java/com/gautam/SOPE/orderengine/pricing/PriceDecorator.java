package com.gautam.SOPE.orderengine.pricing;


import java.math.BigDecimal;

public abstract class PriceDecorator implements PriceComponent {

    protected final PriceComponent wrappedPrice;

    public PriceDecorator(PriceComponent price) {
        this.wrappedPrice = price;
    }

    @Override
    public BigDecimal getCost() {
        return wrappedPrice.getCost();
    }
}
