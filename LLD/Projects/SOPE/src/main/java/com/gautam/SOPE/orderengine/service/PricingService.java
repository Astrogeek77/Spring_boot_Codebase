package com.gautam.SOPE.orderengine.service;

import com.gautam.SOPE.orderengine.pricing.BasePrice;
import com.gautam.SOPE.orderengine.pricing.PackagingDecorator;
import com.gautam.SOPE.orderengine.pricing.PriceComponent;
import com.gautam.SOPE.orderengine.pricing.TaxDecorator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class PricingService {
    public BigDecimal calculateFinalPrice(BigDecimal initialCost, boolean applyTax, boolean extraPackaging) {

        // 1. Start with the base price
        PriceComponent price = new BasePrice(initialCost);

        // 2. Wrap it with Tax if needed
        if (applyTax) {
            price = new TaxDecorator(price);
        }

        // 3. Wrap it with Packaging if needed
        if (extraPackaging) {
            price = new PackagingDecorator(price);
        }

        // 4. Calculate total (The chain executes: Packaging -> Tax -> Base)
        return price.getCost();
    }
}
