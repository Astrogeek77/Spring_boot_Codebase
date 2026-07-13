package com.gautam.Checkout_Discount_System.pattern;

public class NullPlan implements CustomerPlan {
    @Override
    public String getPlanName() {
        return "Guest / No Plan";
    }

    @Override
    public double getDiscountPercentage() {
        return 0.0; // 0% discount (Does nothing safely)
    }
}
