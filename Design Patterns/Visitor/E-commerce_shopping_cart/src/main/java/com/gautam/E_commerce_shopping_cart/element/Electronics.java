package com.gautam.E_commerce_shopping_cart.element;

import com.gautam.E_commerce_shopping_cart.pattern.ShoppingCartVisitor;

public class Electronics implements ItemElement {
    private final double price;
    private final double weight; // in kg

    public Electronics(double price, double weight) {
        this.price = price;
        this.weight = weight;
    }

    public double getPrice() { return price; }
    public double getWeight() { return weight; }

    @Override
    public double accept(ShoppingCartVisitor visitor) {
        return visitor.visit(this);
    }
}
