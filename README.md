
🔗 URL Shortener Microservices with API Gateway
This project implements a robust URL Shortening Service using a microservices architecture in Spring Boot, with internal communication through both REST (RestTemplate) and gRPC (Protocol Buffers).

📦 Project Modules
Module	Port	Description
Shorten Service	8080	Accepts long URLs and generates shortened codes
Redirect Service	8081	Redirects short codes to original long URLs
API Gateway	8082	Unified entry point, routes traffic to services

⚙️ Tech Stack
Java 17

Spring Boot 3.x

Spring Cloud Gateway

gRPC (Protocol Buffers)

RestTemplate

Maven

Docker + Docker Compose

Postman (for testing)

🏗️ Architecture Overview
plaintext
Copy
Edit
Client (Browser/Postman)
        ↓
   API Gateway (8082)
    /             \
Shorten (8080)   Redirect (8081)
The API Gateway exposes a single public endpoint (8082)

Internal services (Shorten and Redirect) communicate using:

✅ REST (RestTemplate)

✅ gRPC (default in Docker setup)

🚀 How to Run
🔧 Prerequisites
Docker & Docker Compose installed

🔄 Run via Docker (gRPC mode default)
Clone the repository:

bash
Copy
Edit
git clone https://github.com/Waseeq-Zafar/UrlShortner.git
cd UrlShortner
Start all services using Docker Compose:

bash
Copy
Edit
docker compose up --build
Access endpoints:

Shorten: http://localhost:8080

Redirect: http://localhost:8081

API Gateway: http://localhost:8082


📬 API Usage (via API Gateway)
✅ Create Short URL
Endpoint: POST http://localhost:8082/api/create

Request Body:

json
Copy
Edit
{
  "longUrl": "https://www.example.com/some/long/path"
}
Response:

json
Copy
Edit
{
  "shortUrl": "http://localhost:8082/000001"
}
🔁 Redirect to Original URL

Open http://localhost:8082/000001 in browser

OR use Postman to send a GET request

You’ll be redirected to the original long URL

🛠️ Switch Communication Mode
Default in Docker is gRPC

To switch to REST:

Comment out gRPC beans in both services

Enable RestTemplate-based communication

Change application.properties accordingly:

properties
Copy
Edit
grpc.client.urlShortener.address=static://shorten-service:9090  # gRPC
# For REST, set rest.url=http://shorten-service:8080
🐳 Docker Compose File (pre-configured for gRPC)
yaml
Copy
Edit
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
    
🔐 Security
Only the API Gateway is exposed to external clients (port 8082)

Internal services are private and only communicate over the Docker network

gRPC communication uses service names (e.g., shorten-service:9090)

🧪 Testing
Use Postman or curl to test:

Shortening URLs

Redirecting from short URLs

✨ Summary
This URL Shortener Microservices project supports both:

✅ REST using Spring’s RestTemplate

✅ gRPC using Protocol Buffers and Spring Boot

It is containerized with Docker and ready for production-style deployments with separation of concerns, internal-only services, and API Gateway routing.

