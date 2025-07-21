# 🔗 URL Shortener - Java + HTML/CSS/JS

A full-stack URL shortener application built using **Java (backend)** and **HTML/CSS/JS (frontend)**. It supports anonymous and user-based short URL generation with support for redirection and custom aliases.

---

## 📁 Project Structure

UrlShortner/
├── backend/ # Java backend using HttpServer, JDBC, and H2
├── frontend/ # HTML, CSS, and JavaScript frontend
├── README.md # Project documentation

---

## 🚀 Features

- 🔗 Shorten long URLs instantly
- 👤 Register & Login functionality
- ✏️ Create custom short URLs
- 🔐 Secure password storage with SHA-256
- 💾 Uses H2 database for storage (file-based persistence)
- 📦 Lightweight backend using built-in Java HttpServer
- 📊 Track short URL usage (future scope)
- 🧪 Unit testing with JUnit 5 and Mockito

---

## 🧱 Tech Stack

| Layer     | Technology                          |
|-----------|-------------------------------------|
| Frontend  | HTML, CSS, JavaScript               |
| Backend   | Java, HttpServer, JDBC, H2 Database |
| Testing   | JUnit 5, Mockito                    |
| Build     | Manual (javac) or via IDE           |

---

## 🔧 Getting Started

### 1️⃣ Backend Setup

```bash
cd backend
javac -d bin src/com/linkzy/*.java
java -cp bin com.linkzy.MainServer
