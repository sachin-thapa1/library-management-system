# 📚 Library Management System

![Status](https://img.shields.io/badge/Status-Production--Ready-brightgreen)  
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)  
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)  
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=jsonwebtokens&logoColor=white)  
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)  

A **production-ready backend API** for managing library operations built with **Spring Boot**, **JWT authentication**, and **PostgreSQL**. Supports role-based access, book management, member management, and borrow/return transactions.

---

## 🚀 Features

- ✅ **JWT Authentication & Role-Based Access** (Roles: ADMIN, USER)  
- ✅ **Book Management** (CRUD + cover image upload & download)  
- ✅ **Member Management** (CRUD + search by name)  
- ✅ **Borrowing/Transactions Management** (Borrow, Return, Active Borrowings, Count)  
- ✅ **Spring Security Integration**  
- ✅ **RESTful API Design** with proper exception handling  
- ✅ **PostgreSQL Database Integration**  

---

## 🔗 API Endpoints

### **Authentication**
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register a new user |
| POST | `/api/auth/register-admin` | Register an admin user |
| POST | `/api/auth/login` | Login and receive JWT token |

### **Books**
| Method | Endpoint | Role | Description |
|--------|----------|------|-------------|
| GET | `/api/books` | USER/ADMIN | List all books |
| GET | `/api/books/{id}` | USER/ADMIN | Get book by ID |
| POST | `/api/books` | ADMIN | Add new book |
| PUT | `/api/books/{id}` | ADMIN | Update book details |
| DELETE | `/api/books/{id}` | ADMIN | Delete a book |
| POST | `/api/books/{id}/cover` | ADMIN | Upload cover image |
| GET | `/api/books/{id}/cover` | USER/ADMIN | Download cover image |
| DELETE | `/api/books/{id}/cover` | ADMIN | Delete cover image |
| GET | `/api/books/search?title={title}` | USER/ADMIN | Search books by title |

### **Members**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/members` | List all members |
| GET | `/api/members/{id}` | Get member by ID |
| POST | `/api/members` | Add new member |
| PUT | `/api/members/{id}` | Update member |
| DELETE | `/api/members/{id}` | Delete member |
| GET | `/api/members/search?name={name}` | Search members by name |

### **Borrowings / Transactions**
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/borrowings` | List all borrowings |
| GET | `/api/borrowings/{id}` | Get borrowing by ID |
| POST | `/api/borrowings` | Add new borrowing |
| POST | `/api/borrowings/borrow` | Borrow a book (special endpoint) |
| PUT | `/api/borrowings/{id}/return` | Return a borrowed book |
| PUT | `/api/borrowings/{id}` | Update borrowing |
| DELETE | `/api/borrowings/{id}` | Delete borrowing |
| GET | `/api/borrowings/search` | Search borrowings by status |
| GET | `/api/borrowings/member/{memberId}` | Get borrowings by member |
| GET | `/api/borrowings/active` | Get active borrowings |
| GET | `/api/borrowings/count` | Get total borrowings count |

---

## ⚡ Installation

1. Clone the repo:

```bash
git clone https://github.com/sachin-thapa1/library-management-system.git
cd library-management-system
