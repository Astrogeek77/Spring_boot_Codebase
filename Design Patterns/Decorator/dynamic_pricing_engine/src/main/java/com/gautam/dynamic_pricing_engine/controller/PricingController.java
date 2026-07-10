package com.gautam.dynamic_pricing_engine.controller;

import com.gautam.dynamic_pricing_engine.service.PricingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class PricingController {

    private final PricingService pricingService;

    // Injecting the service we created earlier
    @Autowired
    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping("/calculate-price")
    public Map<String, Object> calculatePrice(
            @RequestParam(defaultValue = "100.0") double basePrice,
            @RequestParam(defaultValue = "false") boolean applyTax,
            @RequestParam(defaultValue = "false") boolean applyPackaging) {

        // Call our Decorator-powered service
        double finalPrice = pricingService.calculateFinalPrice(basePrice, applyTax, applyPackaging);

        // Return a JSON response so it's easy to read
        Map<String, Object> response = new HashMap<>();
        response.put("basePrice", basePrice);
        response.put("taxApplied", applyTax);
        response.put("packagingApplied", applyPackaging);
        response.put("finalCalculatedPrice", finalPrice);

        return response;
    }
}
