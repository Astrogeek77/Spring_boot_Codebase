package com.gautam.dynamic_pricing_engine.service;


import com.gautam.dynamic_pricing_engine.component.BasePrice;
import com.gautam.dynamic_pricing_engine.component.PriceComponent;
import com.gautam.dynamic_pricing_engine.decorator.PackagingDecorator;
import com.gautam.dynamic_pricing_engine.decorator.TaxDecorator;
import org.springframework.stereotype.Service;

@Service
public class PricingService {

    public double calculateFinalPrice(double initialPrice, boolean applyTax, boolean applyPackaging) {

        // 1. Start with the core object
        PriceComponent finalPrice = new BasePrice(initialPrice);

        // 2. Wrap it in Tax if necessary
        if (applyTax) {
            finalPrice = new TaxDecorator(finalPrice);
        }

        // 3. Wrap it in Packaging if necessary
        if (applyPackaging) {
            finalPrice = new PackagingDecorator(finalPrice);
        }

        // 4. Calculate the final result. The call will cascade through the wrappers!
        return finalPrice.calculatePrice();
    }
}
