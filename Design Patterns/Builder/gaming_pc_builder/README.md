# Custom PC Builder (Builder Design Pattern)

A Spring Boot application that demonstrates the **Builder Design Pattern**. This project simulates a custom PC configuration system to solve the problem of creating complex objects with many optional parameters (like a gaming computer vs. an office computer).

## 📖 Project Overview



**The Problem: The "Telescoping Constructor" Anti-Pattern**
When creating a complex object like a Computer, some parts are required (CPU, RAM), but many are optional (GPU, Bluetooth, RGB Lights, Water Cooling).
Without the Builder pattern, you end up with a messy constructor that is hard to read and prone to errors:
```java
// Which 'true' is for Water Cooling? Which is for RGB?
new GamingComputer("Intel i9", "32GB", "Nvidia 4090", true, true, "1TB SSD");
```

**The Solution**: The Builder Pattern separates the construction of a complex object from its representation. 
It allows you to create the object step-by-step using a readable, fluent interface:
```java
new GamingComputer.ComputerBuilder("Intel i9", "32GB")
    .setGPU("Nvidia 4090")
    .enableRGB()
    .build();
```

## 🛠️ Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot 3.x
* **Build Tool:** Maven
* **Concepts:** Creational Design Patterns, Fluent Interfaces, Static Inner Classes

## 📂 Project Structure
```
src/main/java/com/gautam/builder/
│
├── model/
│   └── GamingComputer.java   # <--- The Complex Object & Static Builder Class
│
├── controller/
│   └── PCController.java     # <--- The Client (Constructs different PCs)
│
└── BuilderApplication.java
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
4. **Test the Endpoint** Open your browser or Postman and go to:
``` http://localhost:8080/api/pc/build ```

## 📡 API Reference

### Build Demo Computers
Constructs two different PC configurations (a basic Office PC and a High-End Gaming Rig) to demonstrate how the Builder handles optional parameters.

* **URL:** `/api/pc/build`
* **Method:** `GET`
* **Response Example:**
    ```json
    [
      "Office Build: ComputerSpecs [CPU=Intel i5, RAM=8GB, GPU=null, WaterCooling=false, RGB=false, Overlooked=false]",
      "Gaming Beast: ComputerSpecs [CPU=AMD Ryzen 9, RAM=64GB, GPU=Nvidia RTX 4090, WaterCooling=true, RGB=true, Overlooked=true]"
    ]
    ```

## 🧠 Builder Pattern Implementation Details

1.  **The Static Inner Class**:
    We create `public static class ComputerBuilder` inside the main class. This holds the temporary state while the object is being configured.

2.  **Private Constructor**:
    The constructor of the main `GamingComputer` class is private:
    ```java
    private GamingComputer(ComputerBuilder builder) { ... }
    ```
    This **forces** the client code to use the Builder; they cannot call `new GamingComputer()` directly.

3.  **Method Chaining (The Fluent API)**:
    Setter methods in the Builder return `this` (the builder object itself) instead of `void`. This allows methods to be chained together in a single line of code:
    ```java
    public ComputerBuilder setGPU(String GPU) {
        this.GPU = GPU;
        return this; // <--- Allows .
    }    
    ```
4. **Mutability:** Once .build() is called, the GamingComputer object is created. Since there are no public setters in the main class,
   the resulting object is immutable (cannot be changed), which is safer for multi-threaded environments.    
---
Created for learning the Builder Design Pattern in Java Spring Boot.
