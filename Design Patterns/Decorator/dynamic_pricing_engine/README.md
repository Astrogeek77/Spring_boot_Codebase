# SOPE - Dynamic Pricing System

## 📖 Project Overview
SOPE (Simple Order Processing Engine) is a Spring Boot application designed to demonstrate the practical implementation of the Decorator Design Pattern. It simulates a dynamic order pricing engine where a base price is calculated and subsequently "decorated" with additional costs, such as taxes or packaging fees, at runtime without altering the underlying object structure.

## 🛠️ Tech Stack
* Java 17+
* Spring Boot 3.x
* Maven

## 📂 Project Structure
The project is logically divided to separate the pattern implementation from the web layer:
* **pricing package:** Contains the core Decorator pattern interfaces and classes (components, base decorators, and concrete decorators).
* **service package:** Contains the business logic that dynamically assembles the decorators based on runtime parameters.
* **controller package:** Exposes the pricing engine to external clients via REST APIs.

## 🚀 How to Run
1. Navigate to the project directory in your terminal.
2. Build and run the application using the Maven wrapper command (e.g., `./mvnw spring-boot:run`).
3. The application will start on the configured port (e.g., port 8081).

## 📡 API Reference
**Endpoint:** `GET /api/orders/calculate-price`

**Query Parameters:**
* `basePrice` (numeric) - The starting price of the order.
* `applyTax` (boolean) - Whether to apply the tax decorator.
* `applyPackaging` (boolean) - Whether to apply the packaging fee decorator.

**Response:**
Returns a JSON object detailing the base price, which boolean flags were triggered, and the final calculated price after passing through the active decorators.

## 🧠 decorator Pattern Implementation Details
The Decorator pattern is used to attach new responsibilities to an object dynamically. In this pricing system, it is implemented through the following conceptual blocks:

* **The Component Interface:** Defines the standard contract for calculating a price. All base prices and decorators must abide by this interface.
* **The Concrete Component:** Represents the fundamental, raw order price before any fees or taxes are applied.
* **The Base Decorator:** An abstract class that implements the Component Interface. Its primary job is to hold a reference to a wrapped component and delegate the initial price calculation to it.
* **The Concrete Decorators:** Specific pricing layers, such as a Tax Decorator or a Packaging Decorator. They extend the Base Decorator by first triggering the wrapped component's calculation, and then adding their specific mathematical logic (like multiplying a percentage or adding a flat fee) to the result.

This architecture allows pricing layers to be stacked dynamically like an onion. A base price can be wrapped by a tax decorator, which is then wrapped by a packaging decorator. The calculation cascades through the layers, resulting in an aggregated final price without requiring a rigid, permanent class hierarchy.
