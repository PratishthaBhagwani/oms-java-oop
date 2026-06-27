package com.oms.app;

import com.oms.db.OrderDAO;
import com.oms.db.ProductDAO;
import com.oms.db.UserDAO;
import com.oms.factory.UserFactory;
import com.oms.model.order.Order;
import com.oms.model.product.Product;
import com.oms.model.user.Admin;
import com.oms.model.user.Customer;
import com.oms.model.user.User;

import java.util.ArrayList;

public class AppContext {

    private static AppContext instance;

    private UserDAO    userDAO    = new UserDAO();
    private ProductDAO productDAO = new ProductDAO();
    private OrderDAO   orderDAO   = new OrderDAO();

    private User loggedInUser = null;

    private AppContext() {
        seedDataIfEmpty();
    }

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }


    private void seedDataIfEmpty() {
        if (userDAO.isEmpty()) {
            Admin admin = new Admin("ADMN-00001", "Admin", "admin@oms.com");
            userDAO.save(admin);

            Customer c = new Customer("CUST-00001", "Arjun Sharma", "arjun@gmail.com");
            c.topUpWallet(10000);
            userDAO.save(c);

            productDAO.save(com.oms.factory.ProductFactory
                    .createElectronics("Samsung Galaxy A55", 32999, 15, 12));
            productDAO.save(com.oms.factory.ProductFactory
                    .createClothing("Cotton Kurta", 799, 50, "M"));
            productDAO.save(com.oms.factory.ProductFactory
                    .createGrocery("Basmati Rice 5kg", 450, 100));

            System.out.println("✅ Sample data seeded to MySQL!");
        }
    }

    public ArrayList<User>    getUsers()    { return userDAO.findAll(); }
    public ArrayList<Product> getProducts() { return productDAO.findAll(); }
    public ArrayList<Order>   getOrders()   { return orderDAO.findAll(); }

    public User getLoggedInUser() { return loggedInUser; }


    public boolean login(String email) {
        User u = userDAO.findByEmail(email);
        if (u != null) {
            loggedInUser = u;
            return true;
        }
        return false;
    }

    public void logout() { loggedInUser = null; }

    public boolean isLoggedIn() { return loggedInUser != null; }


    public boolean addUser(User u) {
        // Duplicate email check
        if (userDAO.findByEmail(u.getEmail()) != null) {
            System.out.println("Email already registered!");
            return false;
        }
        userDAO.save(u);
        return true;
    }
    public void addProduct(Product p) { productDAO.save(p); }
    public void addOrder(Order o)     { orderDAO.save(o); }

    public UserDAO    getUserDAO()    { return userDAO; }
    public ProductDAO getProductDAO() { return productDAO; }
    public OrderDAO   getOrderDAO()   { return orderDAO; }
}