# E-Commerce Order Management System

A Java-based console application I built to strengthen my OOP concepts and learn design patterns practically.

## Why I built this

I wanted to go beyond textbook theory and build something that actually works. This project helped me understand how real-world applications are structured — with proper separation of concerns, reusable components, and extensible design.

## What it does

- Register and login as a customer or admin
- Browse products (Electronics, Clothing, Grocery)
- Add items to cart and view total with tax
- Place orders using UPI, Credit Card, Wallet, or Cash on Delivery
- Get email and SMS notifications when order status changes
- Admin can add new products and update order statuses
- Data persists across sessions using MySQL database

## Design Patterns I implemented

**Builder** — used for creating Order objects which have many fields. Makes object creation readable and avoids constructor confusion.

**Strategy** — used for payment methods. Each method (UPI, Card, Wallet, COD) is a separate class implementing a common interface. Adding a new payment method requires zero changes to existing code.

**Observer** — used for notifications. When an order status changes, Email and SMS notifiers fire automatically without the Order class knowing who is listening.

**Factory** — used for creating Users and Products. Auto-generates unique IDs using UUID and centralizes object creation.

**Singleton** — used for both AppContext (central data store) and DBConnection (single database connection throughout the app).


## OOP concepts covered

- Abstract classes — User and Product are abstract since you can never have "just a user" or "just a product"
- Interfaces — Taxable interface ensures every product type implements tax calculation
- Inheritance — Customer, Admin, Seller all extend User; Electronics, Clothing, Grocery extend Product
- Polymorphism — calculateTax() behaves differently for each product type
- Encapsulation — all fields are private, accessed only through getters

## Project structure

```
src/com/oms/
├── model/
│   ├── user/       User, Customer, Admin, Seller
│   ├── product/    Product, Electronics, Clothing, Grocery, Taxable
│   └── order/      Order (Builder), CartItem, ShoppingCart, OrderStatus
├── payment/        PaymentStrategy, UpiPayment, CreditCardPayment, WalletPayment, CashOnDelivery
├── observer/       OrderObserver, EmailNotifier, SmsNotifier
├── factory/        ProductFactory, UserFactory
├── app/            AppContext (Singleton)
├── ui/             ConsoleMenu, CustomerMenu, AdminMenu
├── db/             DBConnection, UserDAO, ProductDAO, OrderDAO
└── Main.java
```

## How to run

1. Clone the repo
2. Install MySQL and create database using schema.sql
3. Set environment variable: MYSQL_PASSWORD=your_password
4. Open in IntelliJ IDEA
5. Add MySQL JDBC connector jar to project libraries
6. Run Main.java
7. Login with admin@oms.com as admin, or register a new customer account

## Tech used

- Java 21
- MySQL 8.0 — persistent data storage
- JDBC — Java Database Connectivity
- IntelliJ IDEA

 ## Error Handling
- OutOfStockException — thrown when requested quantity exceeds available stock
- InvalidEmailException — thrown when email format is invalid during registration  
- OrderNotFoundException — thrown when an order ID does not exist
- PaymentFailedException — thrown when wallet balance is insufficient

## Known limitations / Future scope
- Passwords stored as plain text — in production, BCrypt hashing would be used
- No input validation on price/stock fields
- Console-based UI — can be extended to REST API with Spring Boot
