# Article Drafting System - Memento Design Pattern

## 📖 Project Overview
This module demonstrates the **Memento Design Pattern** within a Spring Boot application. It simulates an Article Drafting System (like a CMS or a text editor) that allows a user to write an article, make changes, and use an **Undo** feature to revert to previous drafts.

The Memento pattern allows you to capture and save the internal state of an object without exposing its internal structure to the outside world, enabling robust restoration capabilities.

## 🛠️ Tech Stack
* Java 17+
* Spring Boot 3.x
* Maven

## 📂 Project Structure
* **model:** Contains the Originator (`Article`), which is the main object whose state we are modifying and tracking.
* **pattern:** Contains the Memento (`ArticleMemento`), an immutable class that holds the frozen state of an Article at a specific point in time.
* **service:** Contains the Caretaker (`HistoryService`), a Spring component that manages a stack of Mementos to facilitate the undo functionality.
* **controller:** The REST controller that exposes the writing and undoing operations to the client.

## 🚀 How to Run
1. Navigate to the project directory in your terminal.
2. Build and run the application using the Maven wrapper command:
   ```bash
   ./mvnw spring-boot:run
   ```
   The application will start on the configured port (default is usually 8080).

## 📡 API Reference

### 1. View Current Article
**Endpoint:** `GET /api/articles/current`  
**Description:** Returns the current state of the working article.

### 2. Write/Update Article
**Endpoint:** `POST /api/articles/write`  
**Query Parameters:**
* `title` (string) - The new title.
* `content` (string) - The new content.

**Description:** Automatically saves a snapshot (Memento) of the current article into the HistoryService, and then updates the article with the new title and content.
**Response:** Returns the newly updated Article.

### 3. Undo Last Change
**Endpoint:** `POST /api/articles/undo`  
**Description:** Pops the most recent Memento from the HistoryService stack and restores the Article back to that state.
**Response:**
```json
{
  "article": {
    "title": "Previous Title",
    "content": "Previous Content"
  },
  "message": "Undo successful"
}
```

## 🧠 Memento Pattern Implementation Details
The Memento pattern delegates the creation and restoration of states strictly to the object that owns the state, preserving encapsulation. In this system:

* **The Originator (`Article`):** The object that holds the actual working data. It provides a `save()` method that outputs its current state wrapped in a new `ArticleMemento`, and a `restore(ArticleMemento)` method that takes a memento and overwrites its own fields with the memento's data.
* **The Memento (`ArticleMemento`):** A strict, immutable data structure. Once it is instantiated with a title and content, it cannot be changed. It contains no setter methods. This ensures historical integrity.
* **The Caretaker (`HistoryService`):** Manages a `Stack` (LIFO - Last In, First Out) of `ArticleMemento` objects. The caretaker never inspects or modifies the contents of the mementos. It simply acts as a safe deposit box, pushing new mementos onto the stack when the user types, and popping them off when the user clicks "Undo".

---
Created for learning the Bridge Design Pattern in Java Spring Boot.
