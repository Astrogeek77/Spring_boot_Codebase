package com.gautam.E_commerce_shopping_cart.pattern;


import com.gautam.E_commerce_shopping_cart.element.Book;
import com.gautam.E_commerce_shopping_cart.element.Electronics;
import com.gautam.E_commerce_shopping_cart.element.Groceries;
import org.springframework.stereotype.Component;

@Component("taxVisitor")
public class TaxVisitor implements ShoppingCartVisitor {

    @Override
    public double visit(Book book) {
        // Books are tax-free
        return 0.0;
    }

    @Override
    public double visit(Electronics electronics) {
        // Electronics have an 18% tax
        return electronics.getPrice() * 0.18;
    }

    @Override
    public double visit(Groceries groceries) {
        // Groceries have a 5% tax
        return groceries.getPrice() * 0.05;
    }
}
