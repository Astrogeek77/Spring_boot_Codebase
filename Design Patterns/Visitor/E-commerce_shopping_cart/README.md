# Shopping Cart - Visitor Design Pattern

## 📖 Project Overview
This module demonstrates the **Visitor Design Pattern** within a Spring Boot application. It simulates an E-Commerce Shopping Cart containing various item types (Books, Electronics, Groceries) and calculates total Tax and Shipping Costs.

The Visitor pattern allows you to add new operations (like calculating tax, calculating shipping, or applying discounts) to existing object structures without modifying the objects themselves. This strongly adheres to the **Open/Closed Principle**: you can add a new `Visitor` at any time without changing the item classes.

## 🛠️ Tech Stack
* Java 17+
* Spring Boot 3.x
* Maven

## 📂 Project Structure
* **element:** Contains the `ItemElement` interface and the concrete data classes (`Book`, `Electronics`, `Groceries`) that make up the object structure.
* **pattern:** Contains the core Visitor interfaces and implementations (`ShoppingCartVisitor`, `TaxVisitor`, `ShippingVisitor`).
* **controller:** Exposes the checkout functionality via a REST API, iterating over a collection of items and applying the visitors.

## 🚀 How to Run
1. Navigate to the project directory in your terminal.
2. Build and run the application using the Maven wrapper command:
   ```bash
   ./mvnw spring-boot:run
   ```
   The application will start on the configured port (default is usually 8080).

## 📡 API Reference

### Process Cart Checkout
**Endpoint:** `GET /api/cart/checkout`  
**Description:** Iterates through a predefined shopping cart (1 Book, 1 Electronic item, 1 Grocery item), passing both the `TaxVisitor` and `ShippingVisitor` to each item to calculate the final costs.

**Response:**
```json
{
  "subTotal": 1070.0,
  "totalTax": 181.0,
  "totalShipping": 19.5,
  "grandTotal": 1270.5
}
```

## 🧠 Visitor Pattern Implementation Details
The core of the Visitor Pattern relies on a mechanism called **Double Dispatch**. In Java, method overloading is resolved at compile-time, not runtime. To ensure the correct algorithm is applied to the correct item, we must dispatch twice.

* **First Dispatch:** The client calls `item.accept(visitor)`. Java uses polymorphism to call the overridden `accept` method on the correct concrete element (e.g., `Electronics`).
* **Second Dispatch:** Inside the element's `accept` method, it calls `visitor.visit(this)`. Because `this` is explicitly typed as `Electronics`, Java knows exactly which overloaded `visit(Electronics electronics)` method to execute on the Visitor.

### Components in this system:
* **The Element (`ItemElement`):** Declares an `accept` method that takes a visitor.
* **The Concrete Elements (`Book`, `Electronics`, `Groceries`):** Clean data structures. They implement `accept(visitor)` by simply passing themselves back to the visitor.
* **The Visitor (`ShoppingCartVisitor`):** Declares a set of visiting methods that correspond to element classes.
* **The Concrete Visitors (`TaxVisitor`, `ShippingVisitor`):** Spring `@Component` classes that contain the actual business rules. If we ever need to calculate a "Holiday Discount", we just create a `HolidayDiscountVisitor`—we don't touch the `Book` or `Electronics` classes at all!

---
Created for learning the Visitor Design Pattern in Java Spring Boot.
  
