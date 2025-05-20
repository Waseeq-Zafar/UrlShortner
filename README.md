:

🔗 URL Shortener Microservices with API Gateway
This project implements a robust URL Shortening Service using a microservices architecture in Spring Boot. It includes two variations for internal communication: one using RestTemplate (traditional REST API calls) and the other using gRPC (high-performance protocol buffers).

📦 Project Modules
Shorten Service (port 8080) – Accepts long URLs and generates shortened codes.

Redirect Service (port 8081) – Redirects requests from short codes to original long URLs.

API Gateway (port 8082) – Acts as a unified entry point and routes traffic to services.

⚙️ Tech Stack
Java 17

Spring Boot 3.x

Spring Cloud Gateway

gRPC (Protobuf)

RestTemplate

Maven

Postman (for testing)

🏗️ Architecture Overview
java
Copy
Edit
Client (Browser/Postman)
        ↓
   API Gateway (8082)
    /            \
Shorten (8080)   Redirect (8081)
The API Gateway exposes a single endpoint to external clients.

Internal services (Shorten & Redirect) communicate using either:

✅ RestTemplate (HTTP-based)

✅ gRPC (Protocol Buffers-based)

You can easily switch between communication styles depending on your use case.

🚀 How to Run
Clone the Repository

bash
Copy
Edit
git clone https://github.com/Waseeq-Zafar/UrlShortner.git
cd UrlShortner
Start Services (in separate terminals or via IDE)

Shorten Service → http://localhost:8080

Redirect Service → http://localhost:8081

API Gateway → http://localhost:8082

Restrict internal services:
In each service’s application.properties file:

properties
Copy
Edit
server.address=127.0.0.1
🔄 Communication Modes
1. ☁️ REST-based (using RestTemplate)
Enabled by default in many microservice setups.

API Gateway forwards requests to services via HTTP.

2. ⚡ gRPC-based
Uses .proto definitions and stubs.

Services communicate over port 9090.

Fast, compact, and ideal for high-performance environments.

To switch between modes, simply comment/uncomment the relevant service beans and configurations.

📬 API Usage (via API Gateway)
✅ Create Short URL
Endpoint: POST http://localhost:8082/api/create
Body:

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
Paste: http://localhost:8082/000001 into browser or Postman

You’ll be redirected to the original URL (HTTP 302)

🔐 Security
Only API Gateway is exposed externally (port 8082).

Backend services are restricted to localhost.

Internal communication is secure and controlled.

🧪 Testing
Use Postman or cURL to POST long URLs and retrieve short codes.

Test redirection by accessing the short URL in the browser or via GET requests.

✨ Summary
This project includes two working versions of inter-service communication:

REST-based via RestTemplate

gRPC-based using protocol buffers

You can use this as a reference for learning or building production-grade Spring Boot microservices with flexible communication mechanisms.

