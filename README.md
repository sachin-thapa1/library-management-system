```markdown
# Library Management System

A production-ready REST API for managing library operations — built with Spring Boot 3.3, JWT authentication, and PostgreSQL. Delivered to Siddhanath Science Campus, Kanchanpur, covering three departments: BSc.CSIT, BIT, and BSc. Science.

> Source code only — no live deployment. Clone and run locally using the setup instructions below.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot |
| Security | Spring Security + JWT |
| ORM | JPA / Hibernate |
| Database | PostgreSQL |
| Build Tool | Maven |
| Utilities | Lombok |

---

## Features

- **JWT Authentication** — register, login, and secure token-based access
- **Role-Based Access Control** — two roles: `USER` and `ADMIN` with distinct permissions
- **Book Management** — full CRUD with title search and cover image upload/download
- **Member Management** — full CRUD with name-based search
- **Borrow & Return System** — complete transaction lifecycle with due-date logic and active borrowing tracking
- **File Handling** — book cover image upload, download, and deletion with server-side type validation
- **Exception Handling** — custom error responses with consistent HTTP status codes throughout

---

## API Reference

### Auth
| Method | Endpoint | Access | Description |
|---|---|---|---|
| POST | `/api/auth/register` | Public | Register a new user |
| POST | `/api/auth/register-admin` | Public | Register an admin user |
| POST | `/api/auth/login` | Public | Login and receive JWT token |

### Books
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/books` | USER / ADMIN | List all books |
| GET | `/api/books/{id}` | USER / ADMIN | Get book by ID |
| GET | `/api/books/search?title={title}` | USER / ADMIN | Search books by title |
| POST | `/api/books` | ADMIN | Add a new book |
| PUT | `/api/books/{id}` | ADMIN | Update book details |
| DELETE | `/api/books/{id}` | ADMIN | Delete a book |
| POST | `/api/books/{id}/cover` | ADMIN | Upload cover image |
| GET | `/api/books/{id}/cover` | USER / ADMIN | Download cover image |
| DELETE | `/api/books/{id}/cover` | ADMIN | Delete cover image |

### Members
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/members` | USER / ADMIN | List all members |
| GET | `/api/members/{id}` | USER / ADMIN | Get member by ID |
| GET | `/api/members/search?name={name}` | USER / ADMIN | Search members by name |
| POST | `/api/members` | ADMIN | Add a new member |
| PUT | `/api/members/{id}` | ADMIN | Update member |
| DELETE | `/api/members/{id}` | ADMIN | Delete member |

### Borrowings
| Method | Endpoint | Access | Description |
|---|---|---|---|
| GET | `/api/borrowings` | ADMIN | List all borrowings |
| GET | `/api/borrowings/{id}` | ADMIN | Get borrowing by ID |
| GET | `/api/borrowings/active` | ADMIN | Get all active borrowings |
| GET | `/api/borrowings/count` | ADMIN | Get total borrowings count |
| GET | `/api/borrowings/search` | ADMIN | Search borrowings by status |
| GET | `/api/borrowings/member/{memberId}` | ADMIN | Get borrowings by member |
| POST | `/api/borrowings/borrow` | ADMIN | Borrow a book |
| PUT | `/api/borrowings/{id}/return` | ADMIN | Return a borrowed book |
| PUT | `/api/borrowings/{id}` | ADMIN | Update borrowing record |
| DELETE | `/api/borrowings/{id}` | ADMIN | Delete borrowing record |

---

## Known Limitations

- `/api/auth/register-admin` is currently public — in a production deployment this should be secured behind an existing admin token or removed after initial setup.

---

## Getting Started

### Prerequisites
- Java 21
- Maven
- PostgreSQL

### Setup

**1. Create the database**
```sql
CREATE DATABASE library_management;
```

**2. Configure credentials**

Create `src/main/resources/application-dev.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/library_management
spring.datasource.username=YOUR_USERNAME
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.open-in-view=false

jwt.secret=YOUR_JWT_SECRET_MIN_32_CHARS
```

> Never commit real credentials. Add `application-dev.properties` to your `.gitignore`.

**3. Run the application**
```bash
./mvnw spring-boot:run
```

API available at `http://localhost:8080`.

**4. Test with Postman**

First register an admin via `/api/auth/register-admin`, then login via `/api/auth/login` to get your JWT token. Add it to all subsequent requests:
```
Authorization: Bearer <your_token>
```

---

## Role Overview

| Role | Permissions |
|---|---|
| `USER` | View books, members, search, download cover images |
| `ADMIN` | Full access — manage books, members, borrowings, file uploads |

---

## Delivered To

Siddhanath Science Campus, Kanchanpur, Nepal
Departments: BSc.CSIT · BIT · BSc. Science

---

## License

MIT
```

---
