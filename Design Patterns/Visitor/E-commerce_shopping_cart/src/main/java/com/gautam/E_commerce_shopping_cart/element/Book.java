package com.gautam.E_commerce_shopping_cart.element;


import com.gautam.E_commerce_shopping_cart.pattern.ShoppingCartVisitor;

public class Book implements ItemElement {
    private final double price;
    private final String title;

    public Book(String title, double price) {
        this.title = title;
        this.price = price;
    }

    public double getPrice() { return price; }
    public String getTitle() { return title; }

    @Override
    public double accept(ShoppingCartVisitor visitor) {
        // Double Dispatch: The element passes ITSELF to the visitor
        return visitor.visit(this);
    }
}