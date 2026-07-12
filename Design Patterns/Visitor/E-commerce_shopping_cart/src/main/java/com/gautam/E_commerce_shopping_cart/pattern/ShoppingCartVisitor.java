package com.gautam.E_commerce_shopping_cart.pattern;

import com.gautam.E_commerce_shopping_cart.element.Book;
import com.gautam.E_commerce_shopping_cart.element.Electronics;
import com.gautam.E_commerce_shopping_cart.element.Groceries;

public interface ShoppingCartVisitor {
    double visit(Book book);
    double visit(Electronics electronics);
    double visit(Groceries groceries);
}
