package com.oms.app;

import com.oms.factory.ProductFactory;
import com.oms.factory.UserFactory;
import com.oms.model.order.Order;
import com.oms.model.product.Product;
import com.oms.model.user.User;
import com.oms.model.user.Customer;

import java.util.ArrayList;

public class AppContext {
    //Singleton
    private static AppContext instance;

    private ArrayList<User> users=new ArrayList<>();
    private ArrayList<Product>products=new ArrayList<>();
    private ArrayList<Order>orders=new ArrayList<>();
    private User loggedInUser=null;

    private AppContext(){
        loadSampleData();
    }
    public static AppContext getInstance(){
        if(instance==null){
            instance=new AppContext();
        }
        return instance;
    }
    private void loadSampleData()
    {
        //Users
        users.add(UserFactory.createAdmin("Admin","admin@oms.com"));
        Customer c=UserFactory.createCustomer("Arjun Sharma","arjun@gmail.com");
        c.topUpWallet(10000);
        users.add(c);

        //Products
        products.add(ProductFactory.createElectronics(
                "Samsung Galaxy A55", 32999, 15, 12));
        products.add(ProductFactory.createElectronics(
                "Dell Laptop", 54999, 8, 24));
        products.add(ProductFactory.createClothing(
                "Cotton Kurta", 799, 50, "M"));
        products.add(ProductFactory.createClothing(
                "Levi's Jeans", 2499, 30, "32"));
        products.add(ProductFactory.createGrocery(
                "Basmati Rice 5kg", 450, 100));
        products.add(ProductFactory.createGrocery(
                "Toor Dal 1kg", 120, 200));
    }

    public ArrayList<User> getUsers() { return users; }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }

    //Login/Logout
    public boolean login(String email){
        for(User u:users){
            if(u.getEmail().equalsIgnoreCase(email)){
                loggedInUser=u;
                return true;
            }
        }
        return false;
    }
    public void logout(){  loggedInUser=null;  }
    public boolean isLoggedIn()
    {
        return loggedInUser!=null;
    }
    public boolean addUser(User u){
        for(User existing : users){
            if(existing.getEmail().equalsIgnoreCase(u.getEmail())){
                System.out.println("Email already registered!");
                return false;
            }
        }
            users.add(u);
        return true;
        }
    public void addProduct(Product p){  products.add(p);  }
    public void addOrder(Order o){   orders.add(o);   }

}



