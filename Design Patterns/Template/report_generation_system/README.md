# Report Generation System - Template Design Pattern

## 📖 Project Overview
This module demonstrates the **Template Method Design Pattern** within a Spring Boot application. It simulates a Report Generation System capable of producing various types of reports (like PDF and HTML).

The Template pattern defines the skeleton of an algorithm in an abstract superclass while delegating specific implementations of certain steps to its subclasses. This promotes immense code reuse and enforces a strict sequence of operations (e.g., you must always authenticate before fetching data).

## 🛠️ Tech Stack
* Java 17+
* Spring Boot 3.x
* Maven

## 📂 Project Structure
* **pattern:** Contains the core implementation of the Template Pattern.
  * `ReportGenerator`: The abstract base class containing the `final` template method.
  * `PdfReportGenerator` & `HtmlReportGenerator`: Concrete subclasses providing specific implementations for the varying steps.
* **controller:** Exposes the reporting functionality via a REST API, utilizing Spring's map injection to dynamically select the correct concrete template based on user input.

## 🚀 How to Run
1. Navigate to the project directory in your terminal.
2. Build and run the application using the Maven wrapper command:
   ```bash
   ./mvnw spring-boot:run
    ```
   The application will start on the configured port (default is usually 8080).

## 📡 API Reference

### Generate a Report
**Endpoint:** `GET /api/reports/generate/{type}`  
**Path Variable:** `type` (Valid options: `pdf`, `html`)  
**Query Parameter:** `name` (A string representing the name of the report)

**Example Request:**
```http
GET /api/reports/generate/pdf?name=AnnualFinancials
```

**Example Response:**
```json
[
  "--- Starting generation for: AnnualFinancials ---",
  "Step 1: Authenticated user credentials successfully.",
  "Step 2 (PDF): Executing complex SQL queries to fetch PDF data.",
  "Step 3 (PDF): Formatting data into a strict PDF page layout with headers and footers.",
  "Step 4 (PDF): Exporting binary stream as a .pdf file.",
  "--- Report Generation Complete ---"
]
```

## 🧠 Template Pattern Implementation Details
The Template Method pattern is a behavioral pattern built around inheritance. It focuses on keeping the overall algorithm structure centralized. In this system:

* **The Abstract Class (`ReportGenerator`):** Defines the `generateReport()` method. This is the **Template Method**. It is marked as `final` to ensure that no subclass can maliciously or accidentally alter the chronological sequence of generating a report (Authenticate -> Fetch -> Format -> Export).
* **Shared Logic:** The abstract class also provides concrete implementations for steps that are universal, such as the `authenticate()` method. This prevents code duplication across subclasses.
* **Abstract Steps:** The abstract class defines placeholders (`fetchData()`, `formatData()`, `exportReport()`) that serve as hooks. 
* **The Concrete Subclasses (`PdfReportGenerator`, `HtmlReportGenerator`):** These classes extend the abstract class and fill in the missing abstract hooks with their specific logic. They do not dictate *when* they are called; they only dictate *what* they do when the template method reaches their step in the sequence. This is known as the **Hollywood Principle** ("Don't call us, we'll call you").

---
Created for learning the Template Design Pattern in Java Spring Boot.
