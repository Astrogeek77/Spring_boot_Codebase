package com.gautam.E_commerce_shopping_cart.pattern;

import com.gautam.E_commerce_shopping_cart.element.Book;
import com.gautam.E_commerce_shopping_cart.element.Electronics;
import com.gautam.E_commerce_shopping_cart.element.Groceries;
import org.springframework.stereotype.Component;

@Component("shippingVisitor")
public class ShippingVisitor implements ShoppingCartVisitor {

    @Override
    public double visit(Book book) {
        // Books ship for a flat $2
        return 2.0;
    }

    @Override
    public double visit(Electronics electronics) {
        // Electronics cost $5 plus $1 per kg
        return 5.0 + (electronics.getWeight() * 1.0);
    }

    @Override
    public double visit(Groceries groceries) {
        // Perishable groceries require cold shipping ($10), others are $3
        return groceries.isPerishable() ? 10.0 : 3.0;
    }
}
