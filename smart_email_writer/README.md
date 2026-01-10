# 📧 Smart Email Reply Generator

A full-stack AI-powered application that helps users generate professional, casual, or friendly email replies instantly using Google's **Gemini AI**. The project features a robust **Spring Boot** backend and a modern **React (Material UI)** frontend.

## 🚀 Project Overview

**The Problem:**
Drafting email replies can be time-consuming and mentally draining. Finding the right tone (professional vs. friendly) often takes longer than writing the actual content.

**The Solution:**
This application allows users to:
1. Paste the original email content.
2. Select a desired tone (Professional, Casual, Friendly).
3. Click "Generate" to receive an AI-crafted response in seconds.

## 🛠️ Tech Stack

### Backend
* **Language:** Java 17
* **Framework:** Spring Boot 3.x
* **Reactive Client:** Spring WebFlux (`WebClient`)
* **AI Integration:** Google Gemini API
* **Build Tool:** Maven

### Frontend
* **Library:** React.js
* **UI Framework:** Material UI (MUI)
* **State Management:** React Hooks (`useState`)
* **HTTP Client:** Axios (and Fetch API)

## 📂 Project Structure

```text
smart_email_writer/
├── src/main/java/com/email/writer/
│   ├── controller/
│   │   └── EmailGeneratorController.java  # REST Endpoint for frontend
│   ├── service/
│   │   └── EmailGeneratorService.java     # Handles Gemini API logic
│   ├── model/
│   │   └── EmailRequest.java              # DTO for incoming data
│   └── SmartEmailWriterApplication.java
│
├── src/main/resources/
│   └── application.properties             # Config (API Keys)
│
└── frontend/ 
    ├── src/
    │   ├── components/
    │   │   └── SEG_main.jsx               # Main React UI Component
    │   └── App.js
    └── package.json
```

## ⚙️ Setup & Installation

### 1. Backend Setup (Spring Boot)

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/Astrogeek77/Spring_boot_projects.git](https://github.com/Astrogeek77/Spring_boot_projects.git)
    cd Spring_boot_projects/smart_email_writer
    ```

2.  **Configure API Key:**
    Open `src/main/resources/application.properties` and add your Google Gemini API key.
    *(If the file doesn't exist, create it inside `src/main/resources`)*:
    ```properties
    gemini.api.url=[https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=](https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=)
    gemini.api.key=YOUR_ACTUAL_GOOGLE_API_KEY_HERE
    ```

3.  **Build and Run:**
    ```bash
    mvn spring-boot:run
    ```
    The backend server will start on `http://localhost:8080`.

### 2. Frontend Setup (React)

1.  **Navigate to the frontend directory:**
    *(Assuming your React app is in a folder named `frontend` or similar inside the project)*
    ```bash
    cd frontend
    ```

2.  **Install Dependencies:**
    This project uses Material UI, so ensure you install the core packages:
    ```bash
    npm install
    npm install @mui/material @emotion/react @emotion/styled
    ```

3.  **Start the Development Server:**
    ```bash
    npm start
    ```
    The application will open in your browser at `http://localhost:3000`.

---

## 📡 API Reference

### Generate Email Reply
This endpoint accepts original email content and a desired tone, then returns an AI-generated response.

* **URL:** `http://localhost:8080/api/email/generate`
* **Method:** `POST`
* **Headers:** `Content-Type: application/json`

#### Request Body
```json
{
  "emailContent": "Hi, I received the invoice but the amount seems incorrect based on our last discussion. Can you check?",
  "tone": "professional"
}
```

#### Response (Plain Text)
```
Dear [Name],

Thank you for bringing this to my attention. I apologize for any confusion regarding the invoice. 

I will review our records immediately against our last discussion and get back to you with an update shortly.

Best regards,
[Your Name]
```

## 🧠 How It Works

1.  **Frontend Input:**
    The user enters the email text and selects a tone (Professional, Casual, Friendly) via the React Material UI interface.

2.  **Prompt Engineering (Java Service):**
    The `EmailGeneratorService` takes the input and constructs a specific prompt for the AI:
    > *"Generate a [tone] email reply for the following email content. Please do not generate a subject line. Original email: [content]"*

3.  **Reactive API Call:**
    The application uses Spring WebFlux's **`WebClient`** to send an asynchronous POST request to the **Google Gemini API**.

4.  **JSON Processing:**
    The Gemini API returns a complex nested JSON structure. The backend uses `ObjectMapper` to traverse the JSON path (`candidates` → `content` → `parts` → `text`) and extracts only the generated reply text.

5.  **Final Output:**
    The clean text string is returned to the React frontend, where it is displayed in the "Generated Reply" box for the user to copy.

## 🧩 Chrome Extension

The project includes a custom Google Chrome Extension that allows users to generate email replies directly from their browser toolbar, eliminating the need to switch tabs or open the web application.

### Features
* **Instant Access:** Click the extension icon to open the generator popup on top of Gmail, Outlook, or any other site.
* **Shared Backend:** Connects to the same running Spring Boot API (`http://localhost:8080`) used by the main React application.
* **Lightweight:** Built with HTML, CSS, and vanilla JavaScript for fast performance.

### 🛠️ Installation Guide (Developer Mode)

Since this extension is a local project, you must install it manually:

1.  **Open Extensions Manager:**
    In Google Chrome, navigate to `chrome://extensions/` (or click the puzzle piece icon -> Manage Extensions).

2.  **Enable Developer Mode:**
    Toggle the **"Developer mode"** switch in the top-right corner of the page.

3.  **Load Unpacked:**
    Click the **"Load unpacked"** button in the top-left menu.

4.  **Select the Folder:**
    Browse to your project directory and select the folder containing the extension files (ensure it contains the `manifest.json` file).

5.  **Pin & Run:**
    Pin the extension to your browser toolbar. Click the icon to launch the popup, paste your email, and click Generate!

**Note:** Ensure your Spring Boot backend is running locally for the extension to function correctly.

---
Built with ❤️ using Spring Boot, React & Google Gemini.

    
