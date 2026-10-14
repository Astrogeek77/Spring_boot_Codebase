# Concurra: High-Concurrency Flash Sale Engine ⚡

> A high-performance backend system engineered to handle burst traffic for limited inventory sales, ensuring **Zero Over-Selling** and **Data Integrity** under load.

## 📖 Project Overview
**Concurra** is a backend simulation of a "Flash Sale" system (like Ticketmaster or Amazon Prime Day) where thousands of users compete for a limited number of items simultaneously. 

Standard web applications fail in this scenario due to **Race Conditions** (Double Booking) and **Database Overload**. This project solves these specific distributed system challenges using **Pessimistic Locking**, **Rate Limiting**, and **Caching**.

## 🏗️ Architecture
The system follows a multi-layered defense strategy to handle high concurrency:

1.  **Gatekeeper:** A Token-Bucket Rate Limiter intercepts traffic to prevent bot attacks.
2.  **Cache Layer:** A Read-Through cache serves product details instantly, offloading 90% of database reads.
3.  **Concurrency Control:** The Transaction layer uses Row-Level Locking to serialize write operations for inventory.

## 🚀 Key Features & Solutions

| Challenge | Solution Implemented | Technology |
| :--- | :--- | :--- |
| **The "Double Booking" Problem** | Mitigated Race Conditions using **Pessimistic Locking** (`SELECT ... FOR UPDATE`). This guarantees that only one thread can modify stock at a time, preventing negative inventory. | JPA / Hibernate |
| **Thundering Herd / Bots** | Implemented **Rate Limiting** using the **Token Bucket Algorithm**. Restricts aggressive IP addresses to prevent service degradation. | Bucket4j |
| **High Latency on Reads** | Integrated **Write-Through Caching**. Frequent read requests ("Is it available?") are served from memory, while stock updates trigger immediate cache eviction. | Spring Cache |
| **Data Integrity** | Enforced strict **ACID Compliance** with transactional boundaries to ensure no partial updates occur during payment failures. | Spring Transaction |

## 🛠️ Tech Stack
* **Core:** Java 21, Spring Boot 3.x
* **Database:** H2 (In-Memory for demo), compatible with PostgreSQL
* **Concurrency:** `java.util.concurrent`, Spring AOP
* **Resilience:** Bucket4j (Rate Limiting)
* **Testing:** JUnit 5, ExecutorService (for thread simulation)

## 🏃 How to Run

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/yourusername/concurra.git](https://github.com/yourusername/concurra.git)
    ```
2.  **Build the project:**
    ```bash
    mvn clean install
    ```
3.  **Run the application:**
    ```bash
    mvn spring-boot:run
    ```
4.  The server will start on `http://localhost:8080` and automatically seed the database with **iPhone 16 (Stock: 100)**.

## 🔌 API Endpoints

### 1. View Product (Cached)
* **Endpoint:** `GET /api/flashsale/{id}`
* **Behavior:** First request hits DB; subsequent requests hit Cache.
* **Example:**
    ```bash
    curl http://localhost:8080/api/flashsale/1
    ```

### 2. Buy Product (Locked & Rate Limited)
* **Endpoint:** `POST /api/flashsale/{id}/purchase`
* **Behavior:** * Checks Rate Limit (Returns 429 if exceeded).
    * Acquires DB Lock.
    * Updates Stock.
    * Evicts Cache.
* **Example:**
    ```bash
    curl -X POST http://localhost:8080/api/flashsale/1/purchase
    ```

## 🧪 The "Stress Test" (Proof of Concept)
This project includes a dedicated stress test (`ConcurrencyTest.java`) that proves the robustness of the system.

**Scenario:** 100 threads try to buy an item with only 10 stock.
* **Without Locking:** The system sells 100 items (Stock becomes -90). ❌
* **With Concurra Locking:** The system sells exactly 10 items. Requests 11-100 fail gracefully with "Sold Out". ✅

To run this simulation:
```bash
mvn test -Dtest=ConcurrencyTest
```
---
Created by Gautam Jain to demonstrate System Design and Concurrency patterns.
