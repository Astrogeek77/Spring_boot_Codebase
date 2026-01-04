# Smart Home Controller (Facade Pattern Implementation)

A Spring Boot application that demonstrates the **Facade Design Pattern**. This project simulates a smart home system where complex subsystems (Lighting, TV, Sound) are simplified into easy-to-use "modes" (Movie Mode, Morning Mode) through a central Facade.

## 📖 Project Overview

**The Problem:**
In a complex system like a Smart Home, performing a simple task often requires coordinating multiple devices. For example, to watch a movie, the user must manually:
1. Dim the lights.
2. Turn on the TV.
3. Set the TV input to HDMI.
4. Turn on the Sound System.
5. Adjust the volume.

This creates tight coupling between the client (the user) and the complex logic of every individual device.

**The Solution:**
We use the **Facade Pattern** to provide a simplified interface.
* **The Facade:** A `SmartHomeFacade` class that wraps the complexity. It exposes simple methods like `watchMovieMode()` and `goodMorningMode()`.
* **The Subsystems:** The detailed classes (`LightSystem`, `TVSystem`, `SoundSystem`) that handle the actual work.
* **The Client:** Interacts *only* with the Facade, unaware of the complex sequence occurring behind the scenes.

## 🛠️ Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot 3.x
* **Build Tool:** Maven
* **Concepts:** Structural Design Patterns, Dependency Injection, REST APIs

## 📂 Project Structure

```text
src/main/java/com/gautam/smarthome/
│
├── facade/                   # The "Simplifier" Layer
│   └── SmartHomeFacade.java  # <--- The Facade (Entry point for complex logic)
│
├── subsystem/                # The Complex Devices
│   ├── LightSystem.java
│   ├── TVSystem.java
│   └── SoundSystem.java
│
├── controller/               # The Client (REST API)
│   └── HomeController.java   # Calls the Facade
│
└── SmartHomeApplication.java
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
   mvn spring-boot: run
   ```
4. Test the Endpoints Use Postman or cURL to trigger the modes (see below).

### 📡 API Reference

1. Start Movie Mode
Sets the mood for a movie: dims lights, turns on TV (HDMI), and sets sound to moderate volume.
* URL: /api/home/movie/start
* Method: POST
* Response:
  ```text
  Movie Mode Activated: Lights dimmed, TV on HDMI, Sound at 25.
  ```
2. Stop Movie Mode
Restores the house to a neutral state: lights on, TV/Sound off.
* URL: /api/home/movie/stop
* Method: POST
* Response:
  ```text
  Movie Mode Stopped: House restored to normal.
  ```
3. Start Morning Mode
Prepares the house for the day: lights bright, TV on News channel, low volume.
* URL: /api/home/morning
* Method: POST
* Response:
  ```text
  Good Morning! Lights are bright, News is on, Volume is low.
  ```

### 🧠 Facade Pattern Implementation Details  

1. Dependency Injection: The SmartHomeFacade asks Spring to inject the specific subsystems:
   ```java
   public SmartHomeFacade(LightSystem lights, TVSystem tv, SoundSystem sound) { ... }
   ```
2. Encapsulation: The HomeController (Client) does not import LightSystem or TVSystem. It only knows about SmartHomeFacade.
   This means if we upgrade the TV system later, we only change the Facade, not the Controller.
3. Adapter vs. Facade:
   * Adapter (Previous Project): Makes interface A look like interface B (Compatibility).
   * Facade (This Project): Makes a complex system A, B, and C look like a simple interface D (Simplicity).

---
Created for learning the Facade Design Pattern in Java Spring Boot.
   
