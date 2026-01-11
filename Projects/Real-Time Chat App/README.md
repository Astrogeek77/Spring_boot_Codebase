<img width="1916" height="1067" alt="real-time_chat_app_snippet" src="https://github.com/user-attachments/assets/967ebc78-d370-4875-8ea2-a60a79653f78" />

# Real-Time Chat Application with Spring Boot & WebSocket

A lightweight, real-time chat application built using **Spring Boot** for the backend and **plain JavaScript (with Bootstrap)** for the frontend. It uses the **STOMP** protocol over **SockJS** to handle real-time bi-directional communication.

---

## 🚀 Features

* **Real-Time Messaging:** Messages are delivered instantly to all connected users without refreshing the page.
* **Server-Side Timestamping:** The server automatically appends the exact date and time to every message to ensure consistency.
* **Connection Status:** Visual feedback when connected to the WebSocket server.
* **No Database Required:** Uses an in-memory message broker (SimpleBroker) for simplicity.
* **Responsive UI:** Styled with Bootstrap 5 for a clean look on mobile and desktop.

---

## 🛠️ Tech Stack

**Backend:**
* **Java 17+** (or compatible JDK)
* **Spring Boot 3.x** (Web, WebSocket)
* **Maven** (Build Tool)

**Frontend:**
* **HTML5 & CSS3**
* **Bootstrap 5.3.3** (Styling)
* **SockJS** (WebSocket fallback client)
* **Stomp.js** (STOMP protocol implementation)

---

## 📂 Project Structure

```text
src
├── main
│   ├── java
│   │   └── com.chat.app
│   │       ├── config
│   │       │   └── WebSocketConfig.java  # Configures endpoints & broker
│   │       ├── controller
│   │       │   └── ChatController.java   # Handles message routing
│   │       └── model
│   │           └── ChatMessage.java      # POJO (No Lombok)
│   └── resources
│       ├── static
│       └── templates
│           └── chat.html                 # The frontend UI

```

## ⚙️ Setup & Installation

### 1. Prerequisites
Ensure you have the following installed:
* Java Development Kit (JDK) 11 or higher.
* Maven.

### 2. Dependencies (`pom.xml`)
Ensure your `pom.xml` contains the Spring Boot WebSocket starter:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-websocket</artifactId>
    </dependency>
</dependencies>
```

### 3. Running the Application
* Clone the repository or download the source code.
* Open a terminal in the project root.
* Run the application using Maven:
  ```Bash
  mvn spring-boot:run
* The application will start on http://localhost:8080.

## 📖 API & WebSocket Documentation

### 1. WebSocket Configuration (`WebSocketConfig.java`)
* **Endpoint:** `/chat`
    * This is the entry point for clients to connect via SockJS.
    * Allowed Origins: `*` (Configured via `setAllowedOriginPatterns` for development flexibility).
* **Broker:** `/topic`
    * Clients subscribe to paths starting with `/topic` to receive real-time updates.
* **Application Prefix:** `/app`
    * Clients send messages to paths starting with `/app` to be processed by the `@Controller`.

### 2. Message Flow Protocol
1.  **Connect:** * Client initiates a SockJS connection to `http://localhost:8080/chat`.
2.  **Subscribe:** * Client subscribes to `/topic/messages` to listen for broadcasted messages.
3.  **Send:** * Client sends a JSON payload to `/app/sendMessage`.
    * *Payload Format:* `{"sender": "John", "content": "Hello World"}`
4.  **Process (Server-Side):** * The server receives the message.
    * It generates a server-side `timestamp` (e.g., "2023-10-05 14:30").
    * It attaches the timestamp to the message object.
5.  **Broadcast:** * The message broker pushes the updated message (with timestamp) to all subscribers at `/topic/messages`.

### 3. Data Model (`ChatMessage.java`)
The application uses a standard Java POJO (Plain Old Java Object) to structure messages.

| Field | Type | Description |
| :--- | :--- | :--- |
| `sender` | `String` | The name of the user sending the message. |
| `content` | `String` | The actual text content of the chat message. |
| `timestamp` | `String` | The server-generated date and time string. |

## 🖥️ Usage Guide
1. Open your browser and navigate to http://localhost:8080/chat.
2. (Optional) Open a second tab or a different browser window to the same URL to simulate two users.
3. Enter Name: Type your name in the "Your Name" field.
4. Type Message: Enter text in the message box.
5. Send: Click "Send". You will see the message appear instantly in both windows with the server time.
