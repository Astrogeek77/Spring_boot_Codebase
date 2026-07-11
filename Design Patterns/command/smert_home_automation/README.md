# Smart Home - Command Design Pattern

## 📖 Project Overview
This module demonstrates the **Command Design Pattern** within a Spring Boot application. It simulates a Smart Home Automation System where a central remote control manages various devices.

The Command pattern encapsulates requests (like turning a light on or off) into standalone objects. This isolates the component that issues the command (the Invoker) from the component that executes the logic (the Receiver). Because requests are objects, we can easily queue them, log them, or reverse them via an Undo feature.

## 🛠️ Tech Stack
* Java 17+
* Spring Boot 3.x
* Maven

## 📂 Project Structure
* **receiver:** Contains the core components that perform actual physical/business operations (e.g., `Light`).
* **pattern:** Contains the `Command` interface and its concrete implementations (`TurnOnLightCommand`, `TurnOffLightCommand`).
* **invoker:** Contains the `RemoteControl` that triggers the commands and manages the history stack for undo operations.
* **controller:** The client REST controller that intercepts web requests, creates concrete commands, and passes them to the invoker.

## 🚀 How to Run
1. Navigate to the project directory in your terminal.
2. Build and run the application using the Maven wrapper command:
   ```bash
   ./mvnw spring-boot:run
   ```
  The application will start on the configured port (default is usually 8080 or 8081).

## 📡 API Reference

### 1. Turn Light On
**Endpoint:** `POST /api/smarthome/light/on`  
**Description:** Creates a `TurnOnLightCommand` and passes it to the remote control.  
**Response:** `The Light is now ON`

### 2. Turn Light Off
**Endpoint:** `POST /api/smarthome/light/off`  
**Description:** Creates a `TurnOffLightCommand` and passes it to the remote control.  
**Response:** `The Light is now OFF`

### 3. Undo Last Action
**Endpoint:** `POST /api/smarthome/undo`  
**Description:** Triggers the remote control to pop the last executed command from its history stack and call its `undo()` method.  
**Response:** *(Depends on the previous action, e.g., "The Light is now OFF")*

## 🧠 Command Pattern Implementation Details
The Command pattern turns a request into a stand-alone object containing all information about the request. In this system:

* **The Receiver (`Light`):** A Spring `@Component` that knows how to perform the actual business logic (`turnOn()`, `turnOff()`).
* **The Command Interface (`Command`):** Defines a standard contract (`execute()`, `undo()`) that all concrete commands must follow.
* **The Concrete Commands (`TurnOnLightCommand`):** Standard Java objects instantiated at runtime. They hold a reference to the specific Receiver (`Light`) and map the `execute()` method to the Receiver's actual logic.
* **The Invoker (`RemoteControl`):** A Spring `@Component` that asks the command to carry out the request. It does not know *how* the light works; it only knows how to call `execute()`. It also uses a `Stack` to track command history, allowing it to provide a robust `undo()` feature entirely decoupled from the business logic.
* **The Client (`SmartHomeController`):** Configures everything. It pairs the concrete command with its receiver and hands it to the invoker.
