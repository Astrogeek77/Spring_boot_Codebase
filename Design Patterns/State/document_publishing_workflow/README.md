# Document Workflow - State Design Pattern

## 📖 Project Overview
This module demonstrates the **State Design Pattern** within a Spring Boot application. It simulates a Document Management System where a document transitions through various lifecycle states: **Draft**, **Under Review**, and **Published**.

The State pattern allows an object (the Document) to alter its behavior when its internal state changes. Instead of writing massive `if-else` or `switch` statements to check the document's state before performing an action, the state-specific logic is extracted into separate classes.

## 🛠️ Tech Stack
* Java 17+
* Spring Boot 3.x
* Maven

## 📂 Project Structure
* **pattern:** Contains the core State interfaces and classes (`DocumentState`, `DraftState`, `UnderReviewState`, `PublishedState`).
* **model:** Contains the Context object (`Document`) which maintains an instance of a `DocumentState` subclass that defines its current state.
* **controller:** The client REST controller that interacts with the Document and triggers state transitions.

## 🚀 How to Run
1. Navigate to the project directory in your terminal.
2. Build and run the application using the Maven wrapper command:
   ```bash
   ./mvnw spring-boot:run
   ```
  The application will start on the configured port (default is usually 8080 or 8081).


## 📡 API Reference

### 1. Check Document Status
**Endpoint:** `GET /api/documents/status`  
**Description:** Returns the current state of the document.
**Response:**
```json
{
  "currentState": "DraftState"
}
```

### 2. Submit for Review
**Endpoint:** `POST /api/documents/submit`  
**Description:** Attempts to move the document from Draft to Under Review.  
**Response:**
```json
{
  "newState": "UnderReviewState",
  "message": "Document submitted for review. It is now UNDER REVIEW."
}
```

### 3. Approve Document
**Endpoint:** `POST /api/documents/approve`  
**Description:** Attempts to move the document from Under Review to Published. Will return an error message if called while in Draft state.
**Response:**
```json
{
  "newState": "PublishedState",
  "message": "Document approved! It is now PUBLISHED."
}
```

### 4. Reject Document
**Endpoint:** `POST /api/documents/reject`  
**Description:** Attempts to reject the document, sending it back to Draft state from Under Review.
```json
{
  "newState": "PublishedState",
  "message": "Document approved! It is now PUBLISHED."
}
```

## 🧠 State Pattern Implementation Details
The State pattern encapsulates state-specific behavior and shifts the responsibility of state transitions from the Context to the State objects themselves. In this system:

* **The Context (`Document`):** Maintains a reference to one of the concrete state objects. It exposes actions (`submitForReview()`, `approve()`, `reject()`) but delegates the actual execution of those actions to the current state object.
* **The State Interface (`DocumentState`):** Defines the contract. It declares the methods that all concrete states must implement.
* **The Concrete States (`DraftState`, `UnderReviewState`, `PublishedState`):** Each class implements behavior associated with a specific state of the Context.
    * If an action is valid (e.g., approving a document that is under review), the state class performs the action and updates the Context's state to the next step (`document.setState(new PublishedState())`).
    * If an action is invalid (e.g., approving a draft), the state class simply returns an error message, gracefully preventing invalid operations without needing complex conditional logic in the main `Document` class.

---
Created for learning the Proxy Design Pattern in Java Spring Boot.
