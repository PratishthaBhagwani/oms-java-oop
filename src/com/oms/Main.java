package com.oms;

import com.oms.db.DBConnection;
import com.oms.db.UserDAO;
import com.oms.db.ProductDAO;
import com.oms.factory.ProductFactory;
import com.oms.factory.UserFactory;
import com.oms.model.user.Customer;


public class Main {
    public static void main(String[] args)
    {
       DBConnection.getInstance();

        UserDAO userDAO = new UserDAO();
        ProductDAO productDAO = new ProductDAO();

        if (userDAO.isEmpty()) {
            Customer c = UserFactory.createCustomer("Arjun Sharma", "arjun@gmail.com");
            c.topUpWallet(5000);
            userDAO.save(c);

            productDAO.save(ProductFactory.createElectronics(
                    "Samsung Galaxy A55", 32999, 15, 12));

            System.out.println("Sample data added!");
        }
        System.out.println("\n--- Users in Database ---");
        userDAO.findAll().forEach(System.out::println);

        System.out.println("\n--- Products in Database ---");
        productDAO.findAll().forEach(System.out::println);
    }
}
