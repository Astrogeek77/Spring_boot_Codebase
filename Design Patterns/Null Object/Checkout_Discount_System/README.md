# Checkout Discount System - Null Object Design Pattern

## 📖 Project Overview
This module demonstrates the **Null Object Design Pattern** within a Spring Boot application. It simulates an E-Commerce Checkout System where customers might have varying loyalty plans (Premium, Standard) that apply discounts to their cart. 

Instead of returning `null` when a customer is not found in the database (e.g., a guest checkout or invalid ID) and forcing the client application to litter its code with `if (plan != null)` checks to avoid `NullPointerException`s, the system returns a `NullPlan`. This object perfectly implements the required interface but provides a safe, "do nothing" default behavior (0% discount).

## 🛠️ Tech Stack
* Java 17+
* Spring Boot 3.x
* Maven

## 📂 Project Structure
* **pattern:** Contains the `CustomerPlan` interface, the real objects (`PremiumPlan`, `StandardPlan`), and the crucial `NullPlan`.
* **service:** Contains the `CustomerService` acting as a repository/factory that guarantees a non-null object is always returned.
* **controller:** The REST API endpoint that processes the checkout, showcasing the absence of null checks.

## 🚀 How to Run
1. Navigate to the project directory in your terminal.
2. Build and run the application using the Maven wrapper command:
   ```bash
   ./mvnw spring-boot:run
    ```
   The application will start on the configured port (default is usually 8080).

## 📡 API Reference

### Process Checkout
**Endpoint:** `GET /api/checkout/process`  
**Query Parameters:**
* `customerId` (Optional string. Valid mock IDs: `CUST-100`, `CUST-200`)
* `amount` (Required double. e.g., `100.00`)

**Description:** Calculates the final price of an order based on the customer's loyalty plan.

**Scenario A: Valid VIP Customer**
**Request:** `GET /api/checkout/process?customerId=CUST-100&amount=100.0`
**Response:**
```json
{
  "customerId": "CUST-100",
  "planApplied": "VIP Premium Member",
  "originalAmount": 100.0,
  "discountAmount": 20.0,
  "finalPrice": 80.0
}
```

**Scenario B: Unknown/Missing Customer (The Null Object in Action)**
**Request:** `GET /api/checkout/process?customerId=INVALID-ID&amount=100.0`
**Response:**
```json
{
  "customerId": "INVALID-ID",
  "planApplied": "Guest / No Plan",
  "originalAmount": 100.0,
  "discountAmount": 0.0,
  "finalPrice": 100.0
}
```
(Notice how the system gracefully defaulted to 0.0 discount instead of throwing a NullPointerException!)

## 🧠 Null Object Pattern Implementation Details
The Null Object pattern sits at the intersection of polymorphism and safety. It simplifies client code heavily. In this system:

* **The Interface (`CustomerPlan`):** Defines the contract. Every plan must be able to state its name and its discount percentage.
* **The Real Objects (`PremiumPlan`, `StandardPlan`):** Provide the actual business logic for legitimate database records.
* **The Null Object (`NullPlan`):** Implements the exact same interface as the real objects but returns safe, neutral values (`"Guest / No Plan"` and `0.0` for discount). It represents the absence of a real plan.
* **The Factory/Service (`CustomerService`):** The gatekeeper. It intercepts the `null` result from the mock database and replaces it with a `new NullPlan()`.
* **The Client (`CheckoutController`):** Because of polymorphism, the client does not care if it received a `PremiumPlan` or a `NullPlan`. It simply calls `.getDiscountPercentage()` on whatever object it was handed, drastically reducing cyclomatic complexity (fewer `if/else` paths).

---
Created for learning the Null Object Design Pattern in Java Spring Boot. 
