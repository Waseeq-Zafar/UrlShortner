🔗 URL Shortener Microservices with API Gateway & gRPC
This project demonstrates a simple URL Shortening Service built using Spring Boot microservices and an API Gateway with gRPC-based internal communication.

🧩 Microservices Overview
✅ Shorten Service (port 8080): Accepts long URLs and returns shortened codes.

🔁 Redirect Service (port 8081): Redirects the short code to the original long URL.

🚪 API Gateway (port 8082): A unified entry point that routes all external traffic to the appropriate microservice.

📡 gRPC Communication: Shorten and Redirect services communicate internally via gRPC.

🛠 Tech Stack
Java 17

Spring Boot 3.x

Spring Cloud Gateway

gRPC (via grpc-spring-boot-starter)

Maven

Postman (for API testing)

🏗 Architecture
java
Copy
Edit
Client (Browser / Postman)
       |
       v
API Gateway (8082)
  ├── /api/create  --> Shorten Service (8080)
  └── /{shortCode} --> Redirect Service (8081)
🔒 API Gateway (8082) is the only exposed service.

🧱 Internal services (8080, 8081) are bound to 127.0.0.1 and are not directly accessible externally.

📡 Internal communication between Shorten and Redirect is done via gRPC, not HTTP.

🚀 Getting Started
1️⃣ Clone the Repository
bash
Copy
Edit
git clone https://github.com/Waseeq-Zafar/UrlShortner.git
cd UrlShortner
2️⃣ Run All Services
Start each Spring Boot application in a separate terminal or via your IDE:

Shorten Service → http://localhost:8080

Redirect Service → http://localhost:8081

API Gateway → http://localhost:8082

✅ Internal services use gRPC for communication on port 9090.

⚙️ Internal Config Notes
For internal-only access, set this in each internal service (application.properties):

properties
Copy
Edit
server.address=127.0.0.1
To configure gRPC client (used in Redirect Service):

properties
Copy
Edit
grpc.client.urlShortener.address=static://127.0.0.1:9090
grpc.client.urlShortener.negotiationType=PLAINTEXT
To configure gRPC server (inside Shorten Service):

properties
Copy
Edit
grpc.server.port=9090
📬 API Usage (via API Gateway)
✅ 1. Create a Short URL
Endpoint: http://localhost:8082/api/create

Method: POST

Headers: Content-Type: application/json

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
🔁 2. Redirect to Original URL
Paste the returned short URL (e.g., http://localhost:8082/000001) into:

🌐 Browser → You’ll be redirected to the original URL

🧪 Postman (GET request) → You’ll receive a 302 Found redirect

🔐 Security
API Gateway is the only public-facing service (port 8082).

All backend services run on 127.0.0.1.

Internal communication between services is handled securely using gRPC, not HTTP or REST.

🧪 Testing With Postman
Make a POST request to /api/create with a valid long URL.

Copy the returned short URL.

Make a GET request to that short URL to confirm redirection.
