package com.gautam.Checkout_Discount_System.service;

import com.gautam.Checkout_Discount_System.PremiumPlan;
import com.gautam.Checkout_Discount_System.pattern.CustomerPlan;
import com.gautam.Checkout_Discount_System.pattern.NullPlan;
import com.gautam.Checkout_Discount_System.pattern.StandardPlan;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CustomerService {

    // Mock database of customer IDs to their respective plans
    private final Map<String, CustomerPlan> customerDatabase = new HashMap<>();

    public CustomerService() {
        customerDatabase.put("CUST-100", new PremiumPlan());
        customerDatabase.put("CUST-200", new StandardPlan());
    }

    public CustomerPlan getCustomerPlan(String customerId) {
        // Look up the customer.
        CustomerPlan plan = customerDatabase.get(customerId);

        // The magic of the Null Object Pattern:
        // Instead of returning null, we return our safe NullPlan object.
        if (plan == null) {
            return new NullPlan();
        }

        return plan;
    }
}
