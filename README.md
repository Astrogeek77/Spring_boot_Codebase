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