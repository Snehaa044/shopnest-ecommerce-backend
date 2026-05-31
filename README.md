# ShopNest Backend

Spring Boot REST API for an e-commerce platform.

## Tech Stack
- Java 21
- Spring Boot 3.4.0
- Spring Security with JWT
- Spring Data JPA
- MySQL
- Maven

## Features
-  JWT Authentication & Authorization
-  Role-based access (ADMIN / CUSTOMER)
-  Product CRUD (Admin)
-  Category Management (Admin)
-  Shopping Cart
-  Order Placement & History
-  Admin Dashboard (Sales stats, top products)

## API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register customer |
| POST | `/api/auth/login` | Login & get JWT |

### Products
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/products` | Get all products |
| GET | `/api/products/{id}` | Get product by ID |
| GET | `/api/products/search?keyword=` | Search products |
| POST | `/api/products/admin` | Add product (Admin) |
| PUT | `/api/products/admin/{id}` | Update product (Admin) |
| DELETE | `/api/products/admin/{id}` | Delete product (Admin) |

### Cart
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/cart` | Get user cart |
| POST | `/api/cart/add` | Add item to cart |
| PUT | `/api/cart/update/{itemId}` | Update quantity |
| DELETE | `/api/cart/remove/{itemId}` | Remove item |

### Orders
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/orders/place` | Place order |
| GET | `/api/orders/my` | Get my orders |
| GET | `/api/orders/admin/all` | All orders (Admin) |
| PUT | `/api/orders/admin/{id}/status` | Update status (Admin) |

### Categories
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/categories` | Get all categories |
| POST | `/api/categories/admin` | Add category (Admin) |
| PUT | `/api/categories/admin/{id}` | Update category (Admin) |
| DELETE | `/api/categories/admin/{id}` | Delete category (Admin) |

### Dashboard (Admin)
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/admin/dashboard` | Sales stats & top products |

## Setup Instructions

### Prerequisites
- Java 21
- MySQL 8.x
- Maven

### Database

CREATE DATABASE shopnest;