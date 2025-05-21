# 🔗 URL Shortener Microservices with API Gateway

This project implements a robust URL Shortening Service using a **microservices architecture** in **Spring Boot**, with internal communication via both:

- ☁️ **REST** (`RestTemplate`)
- ⚡ **gRPC** (`Protocol Buffers`)

---

## 📦 Project Modules

| Module           | Port | Description                                      |
|------------------|------|--------------------------------------------------|
| **Shorten Service**   | 8080 | Accepts long URLs and generates short codes      |
| **Redirect Service**  | 8081 | Redirects short codes to original long URLs     |
| **API Gateway**       | 8082 | Unified entry point, routes traffic to services |

---

## ⚙️ Tech Stack

- Java 17  
- Spring Boot 3.x  
- Spring Cloud Gateway  
- gRPC (Protocol Buffers)  
- RestTemplate  
- Maven  
- Docker + Docker Compose  
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

- Only **API Gateway** (8082) is publicly exposed.
- Internal services (`Shorten` and `Redirect`) communicate using:
  - ✅ **REST** (`RestTemplate`)
  - ✅ **gRPC** (default in Docker setup)

---

## 🚀 How to Run

### 🔧 Prerequisites

- Docker  
- Docker Compose  

---

### 🐳 Run via Docker (gRPC is default)

#### 1. Clone the repository

```bash
git clone https://github.com/Waseeq-Zafar/UrlShortner.git
cd UrlShortner
```

```run:
docker-compose up --build
```

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

- Visit: `http://localhost:8082/000001`  
- Or use Postman to send a **GET** request  
- You'll be redirected (`HTTP 302`) to the original long URL

---


## 🐳 Docker Compose File (gRPC default)

```yaml
version: '3.8'

services:

  shorten-service:
    container_name: shorten-service
    build:
      context: ./url-shortner-service
    ports:
      - "8080:8080"
    environment:
      - SERVER_PORT=8080
      - SERVER_ADDRESS=0.0.0.0
      - GRPC_SERVER_PORT=9090
      - GRPC_SERVER_ADDRESS=0.0.0.0
    networks:
      - url-network

  redirect-service:
    container_name: redirect-service
    build:
      context: ./url-redirect-service
    ports:
      - "8081:8081"
    environment:
      - SERVER_PORT=8081
      - SERVER_ADDRESS=0.0.0.0
      - grpc.client.urlShortener.address=static://shorten-service:9090
    depends_on:
      - shorten-service
    networks:
      - url-network

  api-gateway:
    container_name: api-gateway
    build:
      context: ./api-gateway
    ports:
      - "8082:8082"
    environment:
      - SERVER_PORT=8082
      - SERVER_ADDRESS=0.0.0.0
    depends_on:
      - shorten-service
      - redirect-service
    networks:
      - url-network

networks:
  url-network:
    driver: bridge
```

---

## 🔐 Security

- Only **API Gateway (8082)** is exposed to external clients  
- Internal services communicate privately via Docker network  
- gRPC uses service discovery by name (`shorten-service:9090`)

---

## 🧪 Testing

Use Postman or `curl` for:

- ✅ Shortening URLs  
- ✅ Redirecting from short URLs  

Example `curl`:

```bash
curl -X POST http://localhost:8082/api/create \
     -H "Content-Type: application/json" \
     -d '{"longUrl": "https://openai.com"}'
```

---

## ✨ Summary

This microservices-based URL Shortener project provides:

- ✅ REST support using `RestTemplate`  
- ✅ gRPC support using `Protocol Buffers`  
- ✅ API Gateway for unified routing  
- ✅ Dockerized setup for gRPC communication  
- ✅ Clear separation of concerns with internal/private services

📦 Perfect for learning or production-style microservices architecture!

---
