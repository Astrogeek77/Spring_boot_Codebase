package com.gautam.E_commerce_shopping_cart.element;


import com.gautam.E_commerce_shopping_cart.pattern.ShoppingCartVisitor;

public interface ItemElement {
    // Accepts a visitor and returns a calculated value (e.g., tax or shipping cost)
    double accept(ShoppingCartVisitor visitor);
}
