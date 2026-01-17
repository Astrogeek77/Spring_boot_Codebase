package com.gautam.SOPE.orderengine.pricing;

import java.math.BigDecimal;

public class BasePrice implements PriceComponent {

    private final BigDecimal originalPrice;

    public BasePrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    @Override
    public BigDecimal getCost() {
        return originalPrice;
    }
}