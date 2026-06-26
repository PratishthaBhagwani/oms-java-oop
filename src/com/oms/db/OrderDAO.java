package com.oms.db;

import com.oms.model.order.*;
import java.sql.*;
import java.util.ArrayList;

public class OrderDAO {
    private Connection conn;

    public OrderDAO(){
        this.conn = DBConnection.getInstance().getConnection();
    }
    public void save(Order order) {
        String orderSql = """
            INSERT INTO orders
            (order_id, customer_id, total_amount,
             payment_method, shipping_address, status)
            VALUES (?, ?, ?, ?, ?, ?)
            ON DUPLICATE KEY UPDATE
                status = VALUES(status)
        """;
        try (PreparedStatement ps = conn.prepareStatement(orderSql)) {
            ps.setString(1, order.getOrderId());
            ps.setString(2, order.getCustomerId());
            ps.setDouble(3, order.getTotalAmount());
            ps.setString(4, order.getPaymentMethod());
            ps.setString(5, order.getShippingAddress());
            ps.setString(6, order.getStatus().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving order: " + e.getMessage());
        }
        String itemSql = """
            INSERT IGNORE INTO order_items
            (order_id, product_id, name, quantity, unit_price)
            VALUES (?, ?, ?, ?, ?)
        """;
        try (PreparedStatement ps = conn.prepareStatement(itemSql)) {
            for (CartItem item : order.getItems()) {
                ps.setString(1, order.getOrderId());
                ps.setString(2, item.getProduct().getProductId());
                ps.setString(3, item.getProduct().getName());
                ps.setInt(4, item.getQuantity());
                ps.setDouble(5, item.getProduct().getFinalPrice());
                ps.addBatch();
            }
            ps.executeBatch();
        } catch (SQLException e) {
            System.out.println("Error saving items: " + e.getMessage());
        }
    }
    public ArrayList<Order> findAll() {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) orders.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
        return orders;
    }
    public ArrayList<Order> findByCustomer(String customerId) {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE customer_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) orders.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("Error finding orders: " + e.getMessage());
        }
        return orders;
    }
    private Order mapRow(ResultSet rs) throws SQLException {
        String orderId = rs.getString("order_id");

        Order order = new Order.Builder()
                .orderId(orderId)
                .customerId(rs.getString("customer_id"))
                .items(new ArrayList<>()) // items separately load honge
                .totalAmount(rs.getDouble("total_amount"))
                .paymentMethod(rs.getString("payment_method"))
                .shippingAddress(rs.getString("shipping_address"))
                .build();
        OrderStatus target = OrderStatus.valueOf(rs.getString("status"));
        if (target != OrderStatus.PENDING) {
            order.setStatus(target);
        }

        return order;
    }
}
