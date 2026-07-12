# Access Rule Evaluator - Interpreter Design Pattern

## 📖 Project Overview
This module demonstrates the **Interpreter Design Pattern** within a Spring Boot application. It simulates a dynamic Access Control system where permissions are evaluated based on complex string rules rather than hardcoded logic.

The Interpreter pattern defines a representation for a grammar (in this case, Boolean logic for roles) and an interpreter that uses this representation to evaluate sentences in the language. To keep the focus on the pattern rather than complex string parsing, the application evaluates rules written in Postfix Notation (e.g., `"ADMIN USER AND"`).

## 🛠️ Tech Stack
* Java 17+
* Spring Boot 3.x
* Maven

## 📂 Project Structure
* **context:** Contains the `UserContext`, which holds the global state (the user's actual assigned roles).
* **pattern:** Contains the `Expression` interface, the Terminal expressions (`RoleExpression`), and the Non-Terminal expressions (`AndExpression`, `OrExpression`).
* **service:** Contains the `RuleParserService` that converts a string rule into an Abstract Syntax Tree (AST) of Expressions.
* **controller:** Exposes a REST API to test rules against specific user profiles.

## 🚀 How to Run
1. Navigate to the project directory in your terminal.
2. Build and run the application using the Maven wrapper command:
   ```bash
   ./mvnw spring-boot:run
   ```
   The application will start on the configured port (default is usually 8080).

## 📡 API Reference

### Evaluate Access Rule
**Endpoint:** `POST /api/access/evaluate`  
**Description:** Evaluates a dynamic rule (in Postfix notation) against a provided list of user roles to determine if access should be granted.
*Note: `"ADMIN USER AND MANAGER OR"` translates to standard logic `(ADMIN AND USER) OR MANAGER`.*

**Request Body:**
```json
{
  "rule": "ADMIN USER AND MANAGER OR",
  "userRoles": ["USER", "MANAGER"]
}
```

**Response:**
```json
{
  "rule": "ADMIN USER AND MANAGER OR",
  "userRoles": [
    "USER",
    "MANAGER"
  ],
  "accessGranted": true
}
```

## 🧠 Interpreter Pattern Implementation Details
The Interpreter pattern transforms a language or string rule into an Abstract Syntax Tree (AST) and evaluates it step-by-step. In this system:

* **The Context (`UserContext`):** Contains information that is global to the interpreter. In our case, it holds the `Set<String>` of roles the user actually possesses.
* **The Abstract Expression (`Expression`):** Declares an `interpret(UserContext)` method. Every node in the AST must implement this interface.
* **The Terminal Expression (`RoleExpression`):** These are the leaf nodes of the AST. They perform the actual concrete work. When `interpret` is called, it checks if its specific assigned role exists within the `UserContext`.
* **The Non-Terminal Expressions (`AndExpression`, `OrExpression`):** These are the branch nodes. They do not check the context directly; instead, they hold references to other expressions (either Terminal or Non-Terminal). When `interpret` is called, they recursively call `interpret` on their child expressions and apply their respective Boolean logic (`&&` or `||`) to the results.
* **The Parser/Client (`RuleParserService`):** Constructs the AST by parsing the input string. It uses a Stack to build the nested tree of `RoleExpression`, `AndExpression`, and `OrExpression` objects, ultimately returning the root node of the tree for evaluation.

---
Created for learning the interpreter Design Pattern in Java Spring Boot.
