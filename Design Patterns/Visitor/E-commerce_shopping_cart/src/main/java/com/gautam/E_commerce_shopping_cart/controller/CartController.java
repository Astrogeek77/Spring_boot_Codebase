package com.gautam.E_commerce_shopping_cart.controller;

import com.gautam.E_commerce_shopping_cart.element.Book;
import com.gautam.E_commerce_shopping_cart.element.Electronics;
import com.gautam.E_commerce_shopping_cart.element.Groceries;
import com.gautam.E_commerce_shopping_cart.element.ItemElement;
import com.gautam.E_commerce_shopping_cart.pattern.ShoppingCartVisitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final ShoppingCartVisitor taxVisitor;
    private final ShoppingCartVisitor shippingVisitor;

    // A mock shopping cart
    private final List<ItemElement> cartItems = Arrays.asList(
            new Book("Design Patterns", 50.0),
            new Electronics(1000.0, 2.5), // $1000, 2.5kg
            new Groceries(20.0, true)     // $20, perishable
    );

    @Autowired
    public CartController(
            @Qualifier("taxVisitor") ShoppingCartVisitor taxVisitor,
            @Qualifier("shippingVisitor") ShoppingCartVisitor shippingVisitor) {
        this.taxVisitor = taxVisitor;
        this.shippingVisitor = shippingVisitor;
    }

    @GetMapping("/checkout")
    public Map<String, Object> checkout() {
        double totalTax = 0.0;
        double totalShipping = 0.0;
        double subTotal = 0.0;

        for (ItemElement item : cartItems) {
            // Apply the tax algorithm
            totalTax += item.accept(taxVisitor);
            // Apply the shipping algorithm
            totalShipping += item.accept(shippingVisitor);

            // Just for the receipt breakdown:
            if(item instanceof Book) subTotal += ((Book) item).getPrice();
            if(item instanceof Electronics) subTotal += ((Electronics) item).getPrice();
            if(item instanceof Groceries) subTotal += ((Groceries) item).getPrice();
        }

        Map<String, Object> receipt = new HashMap<>();
        receipt.put("subTotal", subTotal);
        receipt.put("totalTax", totalTax);
        receipt.put("totalShipping", totalShipping);
        receipt.put("grandTotal", subTotal + totalTax + totalShipping);

        return receipt;
    }
}
