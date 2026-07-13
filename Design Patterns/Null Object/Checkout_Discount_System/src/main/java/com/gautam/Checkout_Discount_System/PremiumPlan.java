package com.gautam.Checkout_Discount_System;

import com.gautam.Checkout_Discount_System.pattern.CustomerPlan;

public class PremiumPlan implements CustomerPlan {
    @Override
    public String getPlanName() {
        return "VIP Premium Member";
    }

    @Override
    public double getDiscountPercentage() {
        return 0.20; // 20% discount
    }
}
