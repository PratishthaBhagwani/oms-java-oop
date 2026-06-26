CREATE DATABASE IF NOT EXISTS oms_db;
USE oms_db;

CREATE TABLE users (
    user_id        VARCHAR(20)  PRIMARY KEY,
    name           VARCHAR(100) NOT NULL,
    email          VARCHAR(100) UNIQUE NOT NULL,
    password_hash  VARCHAR(20)  NOT NULL,
    role           VARCHAR(20)  NOT NULL,
    wallet_balance DOUBLE       DEFAULT 0.0,
    store_name     VARCHAR(100)
);

CREATE TABLE products (
    product_id      VARCHAR(20)   PRIMARY KEY,
    name            VARCHAR(100)  NOT NULL,
    base_price      DECIMAL(10,2) NOT NULL,
    stock_qty       INT           NOT NULL,
    category        VARCHAR(20)   NOT NULL,
    warranty_months INT,
    size            VARCHAR(10),
    expiry_date     DATE
);

CREATE TABLE orders (
    order_id         VARCHAR(50)   PRIMARY KEY,
    customer_id      VARCHAR(20)   NOT NULL,
    total_amount     DECIMAL(10,2) NOT NULL,
    payment_method   VARCHAR(20)   NOT NULL,
    shipping_address VARCHAR(200)  NOT NULL,
    status           VARCHAR(20)   DEFAULT 'PENDING',
    FOREIGN KEY (customer_id) REFERENCES users(user_id)
);

CREATE TABLE order_items (
    item_id    INT           PRIMARY KEY AUTO_INCREMENT,
    order_id   VARCHAR(50)   NOT NULL,
    product_id VARCHAR(20)   NOT NULL,
    name       VARCHAR(100)  NOT NULL,
    quantity   INT           NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (order_id)   REFERENCES orders(order_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
);
