package com.oms.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBConnection {
    private static DBConnection instance;
    private Connection connection;

    private static final String URL      = "jdbc:mysql://localhost:3306/oms_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = System.getenv("MYSQL_PASSWORD");

    private DBConnection() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("✅ Connected to MySQL!");
        } catch (SQLException e) {
            throw new RuntimeException("DB Connection failed: " + e.getMessage());
        }
    }
    public static DBConnection getInstance() {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

}
