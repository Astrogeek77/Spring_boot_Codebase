package com.gautam.Checkout_Discount_System.pattern;

public class StandardPlan implements CustomerPlan {
    @Override
    public String getPlanName() {
        return "Standard Member";
    }

    @Override
    public double getDiscountPercentage() {
        return 0.10; // 10% discount
    }
}
