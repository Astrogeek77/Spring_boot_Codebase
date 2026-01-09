# Payment Gateway System (Bridge Pattern Implementation)

A Spring Boot application that demonstrates the **Bridge Design Pattern**. This project solves the "Class Explosion" problem by separating the **Payment Method** (Abstraction) from the **Payment Gateway** (Implementation), allowing them to vary independently.

## 📖 Project Overview



**The Problem:**
Imagine you have 2 Payment Types (Credit Card, NetBanking) and 2 Gateways (PayPal, Stripe).
If you use standard inheritance, you need a class for every combination:
* `PayPalCreditCard`
* `StripeCreditCard`
* `PayPalNetBanking`
* `StripeNetBanking`

If you add a new Gateway (e.g., Razorpay), you have to create new classes for *every* payment type. This grows exponentially ($N \times M$ classes).

**The Solution:**
We use the **Bridge Pattern** to decouple the abstraction from the implementation.
* **Abstraction:** The `Payment` class (and its subclasses like `CardPayment`) defines *what* we are doing.
* **Implementation:** The `PaymentGateway` interface (and subclasses like `PaypalGateway`) defines *how* we execute it.
* **The Bridge:** The Abstraction holds a reference (bridge) to the Implementation (`protected PaymentGateway gateway`).

## 🛠️ Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot 3.x
* **Build Tool:** Maven
* **Concepts:** Structural Design Patterns, Composition over Inheritance

## 📂 Project Structure

```text
src/main/java/com/gautam/bridge/
│
├── abstraction/              # The "What" (Refined Abstractions)
│   ├── Payment.java          # <--- The Bridge Holder
│   ├── CardPayment.java
│   └── NetBankingPayment.java
│
├── implementation/           # The "How" (Concrete Implementations)
│   ├── PaymentGateway.java   # <--- The Interface
│   ├── PaypalGateway.java
│   └── StripeGateway.java
│
├── controller/
│   └── BridgeController.java # Tests the combinations
│
└── BridgeApplication.java
```

### 🚀 How to Run

Prerequisites
* Java Development Kit (JDK) 17 or higher
* Maven

Steps
1. Clone the repository
   ```bash
   git clone https://github.com/Astrogeek77/Spring_boot_projects.git
   cd Spring_boot_projects
   ```
2. Build the project
   ```bash
   mvn clean install
   ```
3. Run the application
   ```bash
   mvn spring-boot:run
   ```
4. Access the Feed Open your browser or Postman and go to: http://localhost:8080/api/bridge/test

## 📡 API Reference

### Test Bridge Implementation
Executes multiple combinations of payments (Card via PayPal, NetBanking via Stripe, etc.) to demonstrate flexibility.

* **URL:** `/api/bridge/test`
* **Method:** `GET`
* **Response:** HTML log showing the sequence of operations.

### Console Output
Check your terminal to see the Bridge working in real-time. Notice how the Payment abstraction (left side) delegates the heavy lifting to the Gateway implementation (right side):
```text
--- Starting Card Payment ---
Step 1: Validate Card Number
PayPal: Processing Credit Card request securely.

--- Starting Net Banking ---
Step 1: Redirect to Bank Login Page
Stripe: Authorizing Net Banking transaction via API.
```

## 🧠 Bridge Pattern Implementation Details

1.  **The Bridge (`Payment.java`)**:
    This abstract class holds the reference to the other side of the bridge. It delegates the actual work to this interface, rather than implementing it itself.
    ```java
    public abstract class Payment {
        protected PaymentGateway gateway; // <--- The Bridge
        
        public Payment(PaymentGateway gateway) {
            this.gateway = gateway;
        }
    }
    ```

2.  **Independent Variation**:
    This is the main benefit.
    * **Want to add Apple Pay?** Just add `ApplePayGateway` in the `implementation` package. You don't need to change `CardPayment`.
    * **Want to add Crypto Payment?** Just add `CryptoPayment` in the `abstraction` package. It immediately works with PayPal and Stripe.

3.  **Composition > Inheritance**:
    Instead of extending a class (Inheritance), we pass the behavior we want into the constructor (Composition). This prevents the "Cartesian Product" class explosion problem.
    ```java
    // Mixing and Matching at runtime
    Payment p = new CardPayment(new StripeGateway());
    ```

---
Created for learning the Bridge Design Pattern in Java Spring Boot.
