package com.gautam.E_commerce_shopping_cart.element;


import com.gautam.E_commerce_shopping_cart.pattern.ShoppingCartVisitor;

public class Groceries implements ItemElement {
    private final double price;
    private final boolean isPerishable;

    public Groceries(double price, boolean isPerishable) {
        this.price = price;
        this.isPerishable = isPerishable;
    }

    public double getPrice() { return price; }
    public boolean isPerishable() { return isPerishable; }

    @Override
    public double accept(ShoppingCartVisitor visitor) {
        return visitor.visit(this);
    }
}
