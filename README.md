# URL Shortener

A scalable URL shortening service built using Spring Boot, MySQL, Redis, and Docker. The application supports URL shortening, high-performance redirection, click analytics, distributed counter aggregation, and caching through Redis.

## Features

* Create shortened URLs using Base62 encoding
* Redirect users from short URLs to original URLs
* Retrieve URL details by ID or short code
* View all stored URL mappings
* URL validation and centralized exception handling
* Redis-based URL caching using the Cache-Aside pattern
* Distributed click analytics using Redis atomic counters
* Scheduled aggregation of analytics data into MySQL
* RESTful API design with layered architecture

---

## Technology Stack

* Java 21
* Spring Boot 4
* Spring Data JPA
* MySQL
* Redis
* Maven
* Docker (upcoming)

---

## System Architecture

```text
Client
   │
   ▼
Spring Boot Application
   │
   ├─────────────► Redis Cache
   │                   │
   │                   ▼
   │            URL Lookup
   │
   ▼
MySQL Database
```

### Redirect Flow

```text
User Request
      │
      ▼
Redis Lookup
      │
 ┌────┴────┐
 │         │
Hit       Miss
 │         │
 ▼         ▼
Return   Query DB
 URL        │
            ▼
      Store in Redis
            │
            ▼
        Return URL
```

---

## Analytics Architecture

To avoid performing a database update for every redirect request, click analytics are collected using Redis atomic counters.

### Redirect Analytics Flow

```text
Redirect Request
       │
       ▼
Redis INCR
(clicks:{shortCode})
       │
       ▼
Redirect User
```

### Scheduled Aggregation

```text
Redis Counter
      │
      ▼
Scheduler
(every 60 seconds)
      │
      ▼
MySQL clickCount Update
      │
      ▼
Counter Reset
```

### Benefits

* Reduces database write amplification
* Supports high traffic workloads
* Uses atomic Redis operations for correctness
* Demonstrates eventual consistency design

---

## Database Schema

### URL_MAPPING

| Column       | Type      |
| ------------ | --------- |
| id           | BIGINT    |
| short_code   | VARCHAR   |
| original_url | TEXT      |
| click_count  | BIGINT    |
| created_at   | TIMESTAMP |

---

## API Endpoints

### Create Short URL

```http
POST /api/urls
```

Request:

```json
{
  "url": "https://google.com"
}
```

Response:

```json
{
  "id": 1,
  "shortCode": "bM",
  "originalUrl": "https://google.com"
}
```

---

### Get URL By ID

```http
GET /api/urls/{id}
```

---

### Get URL By Short Code

```http
GET /api/urls/code/{shortCode}
```

---

### Get All URLs

```http
GET /api/urls
```

---

### Redirect

```http
GET /{shortCode}
```

Example:

```http
GET /bM
```

Response:

```http
302 Found
Location: https://google.com
```

---

## URL Shortening Strategy

The application uses Base62 encoding to generate short codes.

Character Set:

```text
abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789
```

Example:

```text
1   -> b
62  -> ba
100 -> bM
```

This approach provides:

* Deterministic short code generation
* No collisions
* Compact URL representation

---

## Redis Key Structure

```text
url:{shortCode}
```

Example:

```text
url:bM
```

Stores:

```text
https://google.com
```

Analytics:

```text
clicks:{shortCode}
```

Example:

```text
clicks:bM
```

Stores pending click counts before aggregation.

---

## Project Structure

```text
src/main/java
│
├── controller
├── dto
├── entity
├── exception
├── repository
├── service
├── util
└── UrlShortenerApplication
```

---

## Future Improvements

* Docker containerization ✔️
* Docker Compose orchestration ✔️
* Swagger/OpenAPI documentation
* Spring Boot Actuator monitoring
* Nginx load balancing
* Multiple application instances
* Redis distributed locking for scheduler coordination
* Hot URL caching strategy
* Custom domains for shortened URLs

---

## Learning Outcomes

This project demonstrates:

* Spring Boot application architecture
* REST API design
* MySQL database integration
* Redis caching strategies
* Cache-Aside pattern
* Distributed analytics counters
* Eventual consistency
* Scheduled background jobs
* DTO mapping and validation
* Exception handling
* Basic system design concepts
