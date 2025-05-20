# 📎 URL Shortener Microservices with API Gateway

This project demonstrates a simple **URL Shortening Service** built using Spring Boot microservices and an API Gateway.

- 🔗 **Shorten Service** (port `8080`): Accepts long URLs and returns shortened codes.
- 🚦 **Redirect Service** (port `8081`): Redirects the short code to the original long URL.
- 🚪 **API Gateway** (port `8082`): A unified entry point that routes all external traffic to the appropriate microservice.

---

## 🛠️ Tech Stack

- Java 17  
- Spring Boot 3.x  
- Spring Cloud Gateway  
- Maven  
- Postman (for testing)  
- RestTemplate (for internal service communication)

---

## 🏗️ Architecture

Client (Browser / Postman)
|
v
API Gateway (8082)
/
Shorten (8080) Redirect (8081)

yaml
Copy
Edit

- The API Gateway listens on `http://localhost:8082`.  
- External clients only communicate with the **API Gateway**.  
- Internal services run on `localhost` and are not exposed externally.  
- Internal communication between services is done using **`RestTemplate`**.

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/url-shortener.git
cd url-shortener
2. Run All Services
Start each Spring Boot app in separate terminals or from your IDE:

Shorten Service → localhost:8080

Redirect Service → localhost:8081

API Gateway → localhost:8082

📌 Ensure each internal service (8080, 8081) includes the following in its application.properties:

properties
Copy
Edit
server.address=127.0.0.1
This restricts direct external access to the internal ports.

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

🔗 Browser – You'll be redirected to the original URL.

🧪 Postman (GET request) – You’ll receive a 302 Found redirect.

🔐 Security
Only the API Gateway is exposed externally (on port 8082).

Backend services are restricted to 127.0.0.1 and not accessible externally.

Internal communication uses RestTemplate for calling between services.

🧪 Testing with Postman
POST request to /api/create with a JSON body to get the short URL.

Copy the returned short URL.

GET request to the short URL or open it in a browser to test redirection.

