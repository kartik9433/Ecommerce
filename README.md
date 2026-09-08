# Mart — E-Commerce Spring Boot Application

A full-featured e-commerce REST API built with **Spring Boot 3**, **Java 21**, **Spring Security (JWT)**, **Spring Data JPA**, and **MySQL**. Serves a lightweight static storefront (`index.html`, `cart.html`) as the frontend.

## 🚀 Tech Stack

| Layer     | Technology                                        |
|-----------|---------------------------------------------------|
| Backend   | Java 21, Spring Boot 3.5.4                        |
| Security  | Spring Security, JWT (jjwt)                       |
| Persistence| Spring Data JPA / Hibernate + MySQL 8             |
| Validation| Jakarta Bean Validation (`@Valid`, `@NotBlank`)   |
| Monitoring| Spring Boot Actuator                             |
| Frontend  | HTML5 + Bootstrap 5 (CDN) + Vanilla JS            |
| Build     | Maven 3.9.11, Maven Wrapper                       |
| Testing   | JUnit 5, Mockito                                  |
| Docker    | Dockerfile + docker-compose (MySQL + App)         |

## ✨ Features

- 🔐 **JWT Authentication** — register, login, secure endpoints with Bearer tokens
- 🔒 **BCrypt password hashing** — no plaintext passwords
- ✅ **Input validation** — bean validation on all request bodies
- 🛡️ **Global exception handling** — structured JSON error responses (`@RestControllerAdvice`)
- 🛒 **Product catalog** — paginated listing, CRUD
- 📦 **Order management** — place orders as authenticated users, view order history
- 📊 **Actuator health checks** — monitoring endpoints
- 💻 **Static frontend** — product browsing, cart (localStorage), and real backend-backed checkout

## 📁 Project Structure

```
src/main/java/com/kartik/EcommerProject/
├── controller/      # REST controllers (Auth, Product, Order, User)
├── service/         # Business logic
├── repo/            # Spring Data JPA repositories
├── model/           # JPA entities (User, Product, Orders, OrderItem)
├── dto/             # Request/response DTOs
├── security/        # JWT util, filter, security config, user details
└── exception/       # Custom exceptions + global handler

src/main/resources/static/  # Frontend (index.html, cart.html)
postman/                   # Postman collection + environment
```

## 🔌 API Endpoints

Base URL: `http://localhost:9090`

### Auth (Public)
| Method | Endpoint                    | Description                  |
|--------|-----------------------------|------------------------------|
| POST   | `/api/v1/auth/register`     | Register & get JWT token     |
| POST   | `/api/v1/auth/login`        | Login & get JWT token        |

### Products (Public)
| Method | Endpoint                | Description                          |
|--------|-------------------------|--------------------------------------|
| GET    | `/api/v1/products`      | Paginated list (page, size, sortBy)  |
| GET    | `/api/v1/products/{id}` | Get product by ID                    |
| POST   | `/api/v1/products`      | Create product                       |

### Orders (Authenticated)
| Method | Endpoint                        | Description            |
|--------|--------------------------------|------------------------|
| POST   | `/api/v1/orders/place/{userId}`| Place an order         |
| GET    | `/api/v1/orders`               | All orders (paginated) |
| GET    | `/api/v1/orders/user/{userId}` | Orders for a user      |

### Users (Authenticated)
| Method | Endpoint           | Description       |
|--------|--------------------|-------------------|
| GET    | `/api/v1/users`    | Get all users     |

### Monitoring (Public)
| Method | Endpoint            | Description |
|--------|---------------------|-------------|
| GET    | `/actuator/health`  | Health check|

> Authenticated requests require header: `Authorization: Bearer <token>`

## 🚀 Getting Started

### Prerequisites
- Java 21
- Maven 3.9+ (or use the bundled `mvnw` wrapper)
- MySQL 8 (local) **or** Docker

### Option A: Run with Docker (Recommended)

```bash
# Build and start MySQL + App together
docker-compose up --build
```

This starts:
- **MySQL** on port 3306 (db: `ecommerce`)
- **App** on port 9090

Application health: http://localhost:9090/actuator/health
Storefront: http://localhost:9090/

### Option B: Run Locally

1. **Configure MySQL** — create the connection settings via env vars or edit
   `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce
spring.datasource.username=root
spring.datasource.password=your_password
```

2. **Build and run:**

```bash
# Windows
mvnw.cmd clean package
mvnw.cmd spring-boot:run

# Unix / macOS
./mvnw clean package
./mvnw spring-boot:run
```

3. Open http://localhost:9090/

## 🧪 Testing with Postman

A complete Postman collection with environment variables and test scripts is included in the **`postman/`** folder.

1. Open Postman → **Import** → select both:
   - `postman/MartEcommerce.postman_collection.json`
   - `postman/MartEcommerce.postman_environment.json`
2. Select the **"Mart E-commerce Local"** environment from the dropdown
3. Run requests in this order:
   - `🔐 Authentication` → **Register User** (or Login if already registered)
   - The token is stored automatically and applied to subsequent requests
   - Create/sell products, place orders, and verify validation errors

Optional: There's also a **runner-friendly** flow — hit **Run Collection** and all requests will chain via the collection variables.

## 🧑‍💻 Future / Improvement Roadmap

- [ ] Add ADMIN role with `@PreAuthorize` restrictions
- [ ] Product search / filter by category endpoint
- [ ] Order status update endpoint (admin)
- [ ] Server-side cart persistence (DB-backed)
- [ ] Email notifications on order placement
- [ ] Payment gateway integration (demo)

## 📄 License

MIT
