URL Shortener Microservices with API Gateway

This project implements a robust URL Shortening Service using a microservices architecture powered by Spring Boot. It features two variations for internal service communication: one leveraging RestTemplate for traditional REST API calls, and the other utilizing gRPC with protocol buffers for high-performance communication.

Project Modules

Shorten Service (port 8080): Accepts long URLs and generates shortened codes.
Redirect Service (port 8081): Redirects requests from short codes to the original long URLs.
API Gateway (port 8082): Provides a unified entry point and routes traffic to the underlying services.

Technology Stack

Java 17
Spring Boot 3.x
Spring Cloud Gateway
gRPC (Protocol Buffers)
RestTemplate
Maven
Postman (for testing)

Architecture Overview

External clients interact with the system through the API Gateway at port 8082. The Gateway internally routes requests to the Shorten and Redirect services running on ports 8080 and 8081, respectively. Communication between internal services can be configured either via REST-based HTTP calls using RestTemplate or via gRPC on port 9090. Switching between these communication modes is straightforward by enabling or disabling relevant configuration beans.

How to Run

Clone the repository:
Start each service in a separate terminal or using an IDE:

Shorten Service — http://localhost:8080
Redirect Service — http://localhost:8081
API Gateway — http://localhost:8082
Alternatively, for gRPC mode, run:

API Usage

Create Short URL: POST to http://localhost:8082/api/create with JSON body:

{
  "longUrl": "https://www.example.com/some/long/path"
}

Sample response:

{
  "shortUrl": "http://localhost:8082/000001"
}

Redirect: Access the short URL (e.g., http://localhost:8082/000001) in a browser or via GET request to be redirected (HTTP 302) to the original URL.

Security

Only the API Gateway is exposed externally (port 8082). Backend services are bound to localhost, ensuring internal communication remains secure and controlled.

Summary

This project demonstrates a production-ready Spring Boot microservices design featuring flexible inter-service communication through either REST or gRPC, providing a solid reference for building scalable URL shortening services.

docker-compose up --build

git clone https://github.com/Waseeq-Zafar/UrlShortner.git
cd UrlShortner
