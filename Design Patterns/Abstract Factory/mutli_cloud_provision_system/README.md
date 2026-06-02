## Multi-Cloud Infrastructure Provisioner (Abstract Factory Pattern Implementation)
A Spring Boot application that demonstrates the Abstract Factory Design Pattern. 
This project simulates a system that dynamically provisions a cohesive family of cloud infrastructure resources (Compute + Storage) across different providers (AWS, GCP) without tightly coupling the client code to specific provider implementations.

### 📖 Project Overview

* **The Problem:** When building multi-cloud systems, instantiating cloud-specific resources directly (e.g., `new AwsCompute()`) creates tight coupling. More dangerously, a developer might accidentally mix resources from different providers (e.g., combining an AWS EC2 instance with a GCP Cloud Storage bucket), causing systemic failures and deployment errors.
* **The Solution:** We use the Abstract Factory Pattern to act as a "Factory of Factories."
  * **Abstract Factory:** A common `CloudFactory` interface that dictates the creation of a *family* of objects (Compute and Storage).
  * **Concrete Factories:** Classes (`AwsFactory`, `GcpFactory`) that ensure only compatible objects are created together.
  * **Client:** The application requests resources via the factory interface, completely abstracted away from the specific cloud provider's logic.

### 🛠️ Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot 3.x
* **Build Tool:** Maven
* **Concepts:** Creational Design Patterns, Dependency Injection, Loose Coupling, Open/Closed Principle

### 📂 Project Structure

```text
src/main/java/com/resume/abstractfactory/
│
├── product/                  # 1. The Abstract Products
│   ├── Compute.java          # <--- Interface for compute resources
│   └── Storage.java          # <--- Interface for storage resources
│
├── aws/                      # 2. Concrete AWS Family
│   ├── AwsCompute.java       # <--- AWS implementation
│   └── AwsStorage.java       # <--- AWS implementation
│
├── gcp/                      # 3. Concrete GCP Family
│   ├── GcpCompute.java       # <--- GCP implementation
│   └── GcpStorage.java       # <--- GCP implementation
│
├── factory/                  # 4. The Factories
│   ├── CloudFactory.java     # <--- The Abstract Factory Interface
│   ├── AwsFactory.java       # <--- Creates the AWS Family
│   └── GcpFactory.java       # <--- Creates the GCP Family
│
├── controller/               # 5. The Client
│   └── ProvisioningController.java # Dynamically selects the factory
│
└── AbstractFactoryApplication.java
```

## 🚀 How to Run

### Prerequisites
* Java Development Kit (JDK) 17 or higher
* Maven 3.x
* Postman (or any API testing tool)

### Steps
1. **Clone the repository**
   ```bash
     git clone [https://github.com/yourusername/abstract-factory-cloud.git](https://github.com/yourusername/abstract-factory-cloud.git)
     cd abstract-factory-cloud
   ```
2. **Build the project**
  ```bash
   mvn clean install
```
3. **Run the application**
  ```bash
  mvn spring-boot:run
```

4. **Access the API**
   * The REST API runs on:
  ```bash
   http://localhost:8080/api/cloud/provision
```

## 📡 API Reference

### Provision Cloud Environment
Dynamically creates a matched set of compute and storage resources for the requested cloud provider, guaranteeing that cross-provider contamination (e.g., mixing AWS with GCP) cannot occur.

* **URL:** `/api/cloud/provision`
* **Method:** `GET`
* **Query Params:** * `provider` (Supported options: `aws` or `gcp`)

#### Scenario 1: Provisioning an AWS Environment
* **Request:** `/api/cloud/provision?provider=aws`
* **Response (200 OK):**
  ```json
  [
    "AWS EC2 Instance successfully provisioned.",
    "AWS S3 Bucket successfully created."
  ]

#### Scenario 2: Provisioning a GCP Environment
* **Request:** `/api/cloud/provision?provider=gcp`
* **Response (200 OK):**
  ```json
  [
    "GCP Compute Engine instance successfully provisioned.",
    "GCP Cloud Storage bucket successfully created."
  ]
  
## 🧠 System Design & Implementation Details

This project is built to demonstrate how to safely manage families of dependent objects without creating a chaotic, tightly coupled codebase.

### 1. The Abstract Factory Blueprint (`CloudFactory`)
At the core of the pattern is the `CloudFactory` interface. It defines a strict contract: any concrete factory *must* know how to create a complete family of products (`createCompute()` and `createStorage()`). The client interacts only with this interface, never with the concrete implementations.

### 2. Preventing "Cross-Contamination"
The most significant danger in multi-cloud provisioning is mixing components (e.g., trying to attach an AWS EC2 instance to a Google Cloud Storage bucket). 
* **Implementation:** The `AwsFactory` is hardcoded to only return `AwsCompute` and `AwsStorage` objects. The `GcpFactory` acts similarly for GCP. 
* **Benefit:** It is architecturally impossible for the client layer to accidentally mix and match incompatible resources, ensuring environment integrity.

### 3. Dynamic Resolution via Spring Dependency Injection
Instead of writing a massive `switch` statement to decide which factory to instantiate (e.g., `if provider == "aws" new AwsFactory()`), we leverage Spring's advanced Dependency Injection.
* **Implementation:** The controller uses `@Autowired private Map<String, CloudFactory> cloudFactories;`. 
* **Benefit:** Spring automatically collects all beans implementing `CloudFactory` and maps them by their bean name (e.g., `"awsFactory"`). The application dynamically selects the correct factory at runtime using the query parameter.

### 4. The Open/Closed Principle (OCP)
The system is entirely closed for modification but open for extension. 
* **Scenario:** If the business decides to support **Microsoft Azure** tomorrow.
* **Action:** You simply create an `AzureFactory` (annotated with `@Component("azureFactory")`), `AzureCompute`, and `AzureStorage`. 
* **Result:** You do not need to modify a single line of code in the `ProvisioningController`. The new provider will instantly work when a user calls `/api/cloud/provision?provider=azure`.
