package com.gautam.Checkout_Discount_System.controller;

import com.gautam.Checkout_Discount_System.pattern.CustomerPlan;
import com.gautam.Checkout_Discount_System.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CustomerService customerService;

    @Autowired
    public CheckoutController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/process")
    public Map<String, Object> processCheckout(
            @RequestParam(required = false, defaultValue = "UNKNOWN") String customerId,
            @RequestParam double amount) {

        // 1. Fetch the plan (Will NEVER be null)
        CustomerPlan plan = customerService.getCustomerPlan(customerId);

        // 2. Safely invoke methods without checking for null!
        double discountAmount = amount * plan.getDiscountPercentage();
        double finalPrice = amount - discountAmount;

        Map<String, Object> receipt = new HashMap<>();
        receipt.put("customerId", customerId);
        receipt.put("planApplied", plan.getPlanName());
        receipt.put("originalAmount", amount);
        receipt.put("discountAmount", discountAmount);
        receipt.put("finalPrice", finalPrice);

        return receipt;
    }
}
