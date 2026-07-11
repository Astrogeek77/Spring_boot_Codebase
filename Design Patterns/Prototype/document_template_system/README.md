# Document Generator - Prototype Design Pattern

## 📖 Project Overview
This module demonstrates the **Prototype Design Pattern** within a Spring Boot application. It simulates a Document Generation System where users can request standard documents like Invoices or Reports.

Instead of instantiating and configuring a new document object from scratch every time (which could theoretically involve expensive database calls to fetch standard headers, footers, and boilerplate text), the system keeps pre-configured "Prototypes" in memory. When a request comes in, the system clones the prototype and allows the client to modify the copy safely.

## 🛠️ Tech Stack
* Java 17+
* Spring Boot 3.x
* Maven

## 📂 Project Structure
* **model:** Contains the abstract `Document` class implementing `Cloneable`, and the concrete prototypes (`Invoice`, `Report`).
* **registry:** Contains the `DocumentRegistry`, a Spring `@Component` that acts as a cache/factory for managing and cloning the prototypes.
* **controller:** The client REST controller that fetches clones from the registry and customizes them based on user input.

## 🚀 How to Run
1. Navigate to the project directory in your terminal.
2. Build and run the application using the Maven wrapper command:
   ```bash
   ./mvnw spring-boot:run
   ```
   The application will start on the configured port (default is usually 8080).

## 📡 API Reference

### Generate a Cloned Document
**Endpoint:** `POST /api/documents/generate/{type}`  
**Path Variable:** `type` (Valid options: `invoice`, `report`)  
**Query Parameter:** `customTitle` (Optional string to overwrite the cloned document's default title)

**Example Request:**
```http
POST /api/documents/generate/invoice?customTitle=Client A Billing Invoice
```

**Example Response:**
```json
{
  "type": "Invoice",
  "title": "Client A Billing Invoice",
  "content": "Billed To: [Name] | Total: $0.00 | Tax: 0%"
}
```

## 🧠 Prototype Pattern Implementation Details
The Prototype pattern is a creational pattern used to create duplicate objects while keeping performance in mind. In this system:

* **The Prototype Interface (`Cloneable`):** We use Java's native `Cloneable` interface and override the `clone()` method in the abstract `Document` class.
* **The Concrete Prototypes (`Invoice`, `Report`):** These classes define the default state of our "expensive" objects.
* **The Prototype Registry (`DocumentRegistry`):** Instead of scattering `.clone()` calls everywhere, we encapsulate the prototypes inside a registry. When the application starts (`@PostConstruct`), the registry creates one instance of every document type.
* **The Cloning Mechanism:** When the controller asks the registry for a document, the registry retrieves the base instance and calls `.clone()` on it. The controller receives a completely independent copy stored at a new memory address, meaning changes made to the clone (like setting a custom title) do not corrupt the original prototype stored in the registry.

---
Created for learning the Prototype Design Pattern in Java Spring Boot.

   
