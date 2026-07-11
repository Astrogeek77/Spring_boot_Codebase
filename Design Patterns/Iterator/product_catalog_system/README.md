# Product Catalog - Iterator Design Pattern

## 📖 Project Overview
This module demonstrates the **Iterator Design Pattern** within a Spring Boot application. It simulates a Product Catalog system where products are stored in a custom inventory component. 

The Iterator pattern allows external clients (like a REST Controller) to traverse through a collection of objects (Products) without ever needing to know the underlying data structure (whether it's an array, an `ArrayList`, a `LinkedList`, or a custom tree).

## 🛠️ Tech Stack
* Java 17+
* Spring Boot 3.x
* Maven

## 📂 Project Structure
* **model:** Contains the core entity (`Product`).
* **pattern:** Contains the core Iterator interfaces (`ProductIterator`, `ProductCatalog`) and their concrete implementations (`InventoryCatalog` and its private inner class `InventoryIterator`).
* **service:** Contains the business logic that requests the iterator and steps through the elements.
* **controller:** Exposes the catalog traversal functionality to external clients via a REST API.

## 🚀 How to Run
1. Navigate to the project directory in your terminal.
2. Build and run the application using the Maven wrapper command:
   ```bash
   ./mvnw spring-boot:run
   ```
   The application will start on the configured port (default is usually 8080 or 8081).

## 📡 API Reference
**Endpoint:** `GET /api/catalog/products`

**Description:** Triggers the `CatalogService` to request an Iterator from the `InventoryCatalog` and loops through all stored products dynamically.

**Response:**
Returns a JSON array of all products extracted sequentially via the iterator.
```json
[
  {
    "id": "P01",
    "name": "Mechanical Keyboard",
    "price": 120.0
  },
  {
    "id": "P02",
    "name": "Wireless Mouse",
    "price": 50.0
  }
]
```

## 🧠 Iterator Pattern Implementation Details
The Iterator pattern provides a standard way to loop through items while strictly separating the traversal logic from the business logic. In this system:

* **The Iterator Interface (`ProductIterator`):** Defines the standard operations for traversal: `hasNext()` and `next()`.
* **The Aggregate Interface (`ProductCatalog`):** Declares a method for creating an iterator (`createIterator()`).
* **The Concrete Aggregate (`InventoryCatalog`):** A Spring `@Component` that holds the actual data (a `List` of products). It returns a new instance of the concrete iterator when requested.
* **The Concrete Iterator (`InventoryIterator`):** Implemented as a *private inner class* inside `InventoryCatalog`. Because it is an inner class, it has direct access to the catalog's private data structure (the list), but the outside world only sees the generic `ProductIterator` interface.

This architecture ensures the **Single Responsibility Principle** (the collection manages data storage, the iterator manages data traversal) and the **Open/Closed Principle** (you can implement new types of collections and iterators without modifying existing client code).
