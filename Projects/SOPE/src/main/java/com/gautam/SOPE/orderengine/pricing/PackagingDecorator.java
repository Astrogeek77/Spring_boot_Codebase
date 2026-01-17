package com.gautam.SOPE.orderengine.pricing;

import java.math.BigDecimal;

public class PackagingDecorator extends PriceDecorator {

    public PackagingDecorator(PriceComponent price) {
        super(price);
    }

    @Override
    public BigDecimal getCost() {
        return super.getCost().add(new BigDecimal("5.00"));
    }
}