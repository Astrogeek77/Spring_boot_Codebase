# YouTube Notification System (Observer Design Pattern)

A Spring Boot application that demonstrates the **Observer Design Pattern**. This project simulates a YouTube channel notification system where a "Subject" (the Channel) automatically notifies multiple "Observers" (Subscribers) whenever a new video is uploaded.

## 📖 Project Overview

[Image of Observer Design Pattern structure]

**The Problem:**
Imagine a system where multiple distinct components need to know when the state of another component changes.
* **Without Observer:** The Channel would need to hard-code calls to every subscriber (`alice.sendEmail()`, `bob.sendPushNotification()`). If a new user joins, you have to modify the Channel's code. This is **tight coupling**.
* **With Observer:** The Channel just maintains a list of "Listeners". It doesn't care who they are or what they do. It just says "Hey everyone, I have news!"

**The Solution:**
The **Observer Pattern** defines a one-to-many dependency.
* **Subject (Publisher):** Maintains a list of observers and provides methods to add/remove them.
* **Observer (Subscriber):** Defines an interface (`update`) that gets called when the Subject changes.

## 🛠️ Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot 3.x
* **Build Tool:** Maven
* **Concepts:** Behavioral Design Patterns, Event Handling, Decoupling

## 📂 Project Structure

```text
src/main/java/com/gautam/observer/
│
├── subject/                  # The "Broadcaster"
│   ├── Subject.java          # Interface (subscribe, unsubscribe, notify)
│   └── YoutubeChannel.java   # Concrete Class (The actual channel)
│
├── observer/                 # The "Listener"
│   ├── Observer.java         # Interface (update method)
│   └── Subscriber.java       # Concrete Class (The fan)
│
├── controller/
│   └── YoutubeController.java # Simulates the uploading process
│
└── ObserverApplication.java
```

## 🚀 How to Run

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
4. **Test the Endpoint** Open your browser or Postman and go to:
``` [http://localhost:8080/api/pc/build](http://localhost:8080/api/youtube/upload?videoTitle=ObserverPatternDemo) ```

## 📡 API Reference

### Upload Video (Trigger Notification)
Simulates a channel uploading a video. This endpoint creates subscribers, registers them, uploads a video (triggering notifications), unregisters one user, and uploads another video to show the dynamic list management.

* **URL:** `/api/youtube/upload`
* **Method:** `GET`
* **Query Param:** `videoTitle` (String)
* **Response:**
    ```text
    Check the console logs to see the Observer Pattern in action!
    ```

### Console Output
Check your terminal to see the real-time notifications. Notice how the channel iterates through the list and notifies everyone:
```text
---------------------------------
Gautam's Tech is uploading: ObserverPatternDemo
Hey Alice, New Video Alert: ObserverPatternDemo
Hey Bob, New Video Alert: ObserverPatternDemo
Hey Charlie, New Video Alert: ObserverPatternDemo
---------------------------------
Gautam's Tech is uploading: Java 17 Features (Bonus)
Hey Alice, New Video Alert: Java 17 Features (Bonus)
Hey Charlie, New Video Alert: Java 17 Features (Bonus)
```

## 🧠 Observer Pattern Implementation Details

1.  **The Decoupling**:
    The `YoutubeChannel` class has **no idea** that `Alice` or `Bob` exist specifically. It only knows about the `Observer` interface. This loosely coupled design allows the system to scale easily.
    ```java
    private List<Observer> subscribers = new ArrayList<>();
    ```

2.  **The Notification Loop**:
    When the state changes (video uploaded), the subject iterates through its list. This is the heart of the pattern—the subject broadcasts to everyone without knowing who they are.
    ```java
    public void notifySubscribers(String title) {
        for (Observer observer : subscribers) {
            observer.notifed(title);
        }
    }
    ```

3.  **Open/Closed Principle**:
    The system is open for extension but closed for modification. If you want to add a new type of listener (e.g., `EmailNotificationService`), you just implement the `Observer` interface. You **do not** need to change a single line of code in the `YoutubeChannel` class.

    ---
    Created for learning the Observer Design Pattern in Java Spring Boot.
