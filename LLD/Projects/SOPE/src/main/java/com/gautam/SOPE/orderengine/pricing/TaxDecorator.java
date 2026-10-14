package com.gautam.SOPE.orderengine.pricing;

import java.math.BigDecimal;

public class TaxDecorator extends PriceDecorator {

    public TaxDecorator(PriceComponent price) {
        super(price);
    }

    @Override
    public BigDecimal getCost() {
        BigDecimal currentCost = super.getCost();
        // Add 10% tax
        return currentCost.add(currentCost.multiply(new BigDecimal("0.10")));
    }
}
