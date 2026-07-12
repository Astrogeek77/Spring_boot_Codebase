# Payment Processing System - Strategy Design Pattern

## 📖 Project Overview
This module demonstrates the **Strategy Design Pattern** within a Spring Boot application. It simulates an E-Commerce Checkout System that can dynamically switch between different payment algorithms (Credit Card, PayPal, Crypto) at runtime.

The Strategy pattern allows you to encapsulate specific family of algorithms into separate classes, making them interchangeable. This adheres strictly to the **Open/Closed Principle**: if you need to add a new payment method (like Apple Pay), you simply create a new class implementing the strategy interface. You do not need to modify any existing service or controller code.

## 🛠️ Tech Stack
* Java 17+
* Spring Boot 3.x
* Maven

## 📂 Project Structure
* **pattern:** Contains the `PaymentStrategy` interface and its concrete implementations (`CreditCardStrategy`, `PayPalStrategy`, `CryptoStrategy`).
* **service:** Contains the Context (`PaymentService`), which dynamically routes requests to the correct strategy using Spring's Map Injection.
* **controller:** The REST endpoint that receives the checkout payload and triggers the payment process.

## 🚀 How to Run
1. Navigate to the project directory in your terminal.
2. Build and run the application using the Maven wrapper command:
   ```bash
   ./mvnw spring-boot:run
   ```
   The application will start on the configured port (default is usually 8080).

## 📡 API Reference

### Process Payment
**Endpoint:** `POST /api/checkout/pay`  
**Description:** Processes a payment using the strategy dynamically selected via the `paymentMethod` field.

**Request Body:**
```json
{
  "paymentMethod": "paypal",
  "amount": 250.50
}
```
(Valid paymentMethod options are: credit_card, paypal, crypto)

**Example Response:**
```json
{
  "message": "Successfully processed $250.50 via PayPal.",
  "status": "SUCCESS"
}
```

## 🧠 Strategy Pattern Implementation Details
The Strategy pattern decouples the *what* from the *how*. The core of this system relies on Spring Boot's powerful Dependency Injection to avoid traditional factory or switch-statement boilerplate.

* **The Strategy Interface (`PaymentStrategy`):** Defines the universal contract. Every payment method must implement the `pay(double amount)` method.
* **The Concrete Strategies (`CreditCardStrategy`, etc.):** These are isolated algorithms. They are annotated with `@Component("name")` so they are registered in the Spring IoC container with a specific, identifiable key.
* **The Context (`PaymentService`):** This is the orchestrator. Instead of using hardcoded `if-else` blocks to figure out which class to instantiate, it uses Spring's ability to inject a `Map<String, PaymentStrategy>`. Spring automatically finds all beans implementing `PaymentStrategy` and maps their component names to the instances. The Context simply looks up the requested key and delegates the execution.

---
Created for learning the Strategy Design Pattern in Java Spring Boot. 
   
