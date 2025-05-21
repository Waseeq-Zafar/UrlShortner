# 🔗 URL Shortener Microservices with API Gateway

A robust and production-ready URL Shortening Service built using Spring Boot microservices. This system demonstrates two modes of internal communication:

- 🌐 **REST-based** using `RestTemplate`
- ⚡ **gRPC-based** using `Protocol Buffers`

---

## 📦 Project Modules

| Module           | Port | Description                                      |
|------------------|------|--------------------------------------------------|
| **Shorten Service**   | 8080 | Accepts long URLs and generates short codes      |
| **Redirect Service**  | 8081 | Redirects short codes to original long URLs     |
| **API Gateway**       | 8082 | Unified entry point that routes traffic to services |

---

## ⚙️ Tech Stack

- Java 17  
- Spring Boot 3.x  
- Spring Cloud Gateway  
- gRPC (Protocol Buffers)  
- RestTemplate  
- Maven  
- Docker + Docker Compose (for gRPC variant)  
- Postman (for API testing)  

---

## 🏗️ Architecture Overview

```plaintext
Client (Browser/Postman)
        ↓
   API Gateway (8082)
    /             \
Shorten (8080)   Redirect (8081)
```

- Only **API Gateway** is publicly exposed.
- Internal services (Shorten & Redirect) communicate using:
  - ✅ REST (RestTemplate)
  - ✅ gRPC (Protocol Buffers)

You can switch between the two easily based on your needs.

---

## 🚀 How to Run

### 🔧 Clone the Repository

```bash
git clone https://github.com/Waseeq-Zafar/UrlShortner.git
cd UrlShortner
```

---

### ☁️ REST-based Version (No Docker)

Run each module via your IDE or separate terminals:

```bash
cd url-shortner-service
./mvnw spring-boot:run

cd ../url-redirect-service
./mvnw spring-boot:run

cd ../api-gateway
./mvnw spring-boot:run
```

📌 Ensure the following is in `application.properties` for REST mode:
```properties
rest.url=http://shorten-service:8080
# Comment out any gRPC configuration if switching from gRPC
```

---

### 🐳 gRPC-based Version (With Docker)

> Default communication mode in Docker is **gRPC**

Run with Docker Compose:

```bash
docker compose up --build
```

📍 Access Services:
- Shorten: http://localhost:8080  
- Redirect: http://localhost:8081  
- API Gateway: http://localhost:8082  

🛠️ gRPC settings are pre-configured using `.proto` files and stubs.

---

## 🔄 Communication Modes

### ☁️ REST (Default for Local Dev)
- Uses `RestTemplate`
- Easier to test/debug
- Great for traditional setups

### ⚡ gRPC (Default for Docker)
- Uses `.proto` definitions
- Fast and compact
- Suitable for high-throughput systems

📌 **Switching Modes**:  
Comment or uncomment relevant beans and change properties accordingly.

---

## 📬 API Usage (via API Gateway)

### ✅ Create Short URL

- **Endpoint:** `POST http://localhost:8082/api/create`
- **Request Body:**

- **Example url:**
```raw
https://www.hotstar.com/in
```

- **Response:**

```
000001
```

---

### 🔁 Redirect to Original URL

Paste in browser or use Postman:

```
GET http://localhost:8082/000001
```

✅ You’ll be redirected to the original long URL (`HTTP 302 Found`)

---

## 🔐 Security

- Only **API Gateway** is externally exposed (`port 8082`)
- **Shorten** and **Redirect** services are private (inaccessible directly from outside)
- gRPC uses Docker service names (`shorten-service:9090`) for internal communication

---


---

## ✨ Summary

This project demonstrates:

- ✅ Clean microservices separation
- ✅ Flexible communication styles (REST and gRPC)
- ✅ Dockerized setup for gRPC
- ✅ REST setup for simpler development
- ✅ Centralized routing with Spring Cloud Gateway

---

🔁 Feel free to switch communication strategies depending on performance, scalability, or team familiarity.
