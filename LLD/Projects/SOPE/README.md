# Smart Order Processing Engine (SOPE) 🚀

> A robust, event-driven Spring Boot backend demonstrating advanced design patterns and production-ready architecture.

## 📋 Project Overview
This project is a backend simulation of an e-commerce order processing system. It is designed specifically to demonstrate **clean architecture**, **SOLID principles**, and the practical application of **GoF Design Patterns** in a modern Spring Boot ecosystem. 

Unlike simple CRUD applications, SOPE handles complex business logic like dynamic pricing, state transitions, and asynchronous notifications without tight coupling.

## 🏗️ Architecture & Design Patterns
This project moves beyond "Hello World" by implementing specific patterns to solve real architectural problems:

| Pattern | Component | Problem Solved |
| :--- | :--- | :--- |
| **Strategy Pattern** | Payment Service | Allows switching between payment methods (Credit Card, PayPal, BitCoin) at runtime without modifying core logic. Adheres to the **Open/Closed Principle**. |
| **State Pattern** | Order Lifecycle | Manages complex state transitions (`New` -> `Paid` -> `Shipped`) polymorphically, eliminating massive `if-else` blocks and ensuring valid transition rules. |
| **Decorator Pattern** | Pricing Engine | Enables dynamic calculation of order totals by "wrapping" the base price with tax, shipping, or packaging fees in any combination. |
| **Observer Pattern** | Notification System | Uses Spring Events (`ApplicationEventPublisher`) to decouple the Order Service from Email/Inventory services. Allows for **Asynchronous** processing. |
| **Factory (Spring IoC)** | Payment Factory | Leverages Spring's Dependency Injection to automatically detect and inject the correct strategies at runtime. |

## 🛠️ Tech Stack
* **Core:** Java 21, Spring Boot 3.x
* **Data:** Spring Data JPA, H2 Database (In-Memory for demo)
* **Testing:** JUnit 5, Mockito
* **Tools:** Maven, Lombok (Optional), Postman

## 🌟 Key Features
* **Asynchronous Event Handling:** Uses `@Async` to handle non-blocking tasks (like email simulation) to improve API response times.
* **Global Exception Handling:** Centralized error management using `@ControllerAdvice` to return standardized JSON error responses (e.g., `404 Not Found` vs `500 Internal Error`).
* **Extensible Design:** New payment methods or pricing rules can be added by creating new classes, not touching existing code.

## 🚀 How to Run
1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/yourusername/smart-order-engine.git](https://github.com/yourusername/smart-order-engine.git)
    ```
2.  **Build the project:**
    ```bash
    mvn clean install
    ```
3.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```
4.  The server will start on `http://localhost:8080`.

## 🔌 API Endpoints (Testing Guide)

### 1. Payment (Strategy Pattern)
* **POST** `/api/orders/checkout?type=CREDIT_CARD&amount=100`
* *Response:* "Payment processed via CREDIT_CARD"

### 2. Order Lifecycle (State Pattern & Async Events)
* **POST** `/api/orders/create`
    * *Creates a new order. Watch console logs for Async Email/Inventory events.*
* **POST** `/api/orders/{id}/next`
    * *Moves order state forward (New -> Paid -> Shipped).*

### 3. Pricing Engine (Decorator Pattern)
* **GET** `/api/orders/price?cost=100&tax=true&pack=true`
    * *Calculates total cost with Tax (+10%) and Packaging (+$5).*

## 🧪 Testing
Run the comprehensive test suite including Unit Tests for logic and Integration Tests for persistence:
```bash
mvn test
```
---
Created by Gautam Jain as a demonstration of advanced Spring Boot architecture.
