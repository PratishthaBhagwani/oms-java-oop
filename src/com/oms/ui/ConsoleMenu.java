package com.oms.ui;
import com.oms.app.AppContext;
import com.oms.model.user.Admin;
import com.oms.model.user.Customer;

import java.util.Scanner;

public class ConsoleMenu {
    private AppContext context=AppContext.getInstance();
    private Scanner scanner=new Scanner(System.in);
    private CustomerMenu customerMenu=new CustomerMenu(scanner,context);
    private AdminMenu adminMenu=new AdminMenu(scanner,context);

    public void start(){
        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║   E-COMMERCE ORDER MANAGEMENT    ║");
        System.out.println("╚══════════════════════════════════╝");

        while(true){
            if(!context.isLoggedIn()){
                showMainMenu();
            }
            else if(context.getLoggedInUser() instanceof Admin){
                showAdminMenu();
            }
            else if(context.getLoggedInUser() instanceof Customer){
                showCustomerMenu();
            }
        }
    }
    private void showMainMenu(){
        System.out.println("\n1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choice: ");

        String choice=scanner.nextLine();

        switch(choice){
            case "1" -> doLogin();
            case "2" -> doRegister();
            case "3" -> {
                System.out.println("Goodbye !");
                System.exit(0);
            }
            default -> System.out.println("Invalid Choice !");
        }
    }
    private void doLogin()
    {
        System.out.print("Enter email: ");
        String email=scanner.nextLine();

        if(context.login(email)){
            System.out.println("Welcome, " +
                    context.getLoggedInUser().getName() + "!");
        }else {
            System.out.println("Email not found!");
        }
    }
    private void doRegister()
    {
        System.out.print("Enter name: ");
        String name=scanner.nextLine();
        System.out.print("Enter email: ");
        String email=scanner.nextLine();

        Customer c=com.oms.factory.UserFactory.createCustomer(name,email);
        boolean added=context.addUser(c);
        if(added) {
            System.out.println("Registered! ID: " + c.getUserId());
        }
    }
    private void showCustomerMenu(){
        Customer c=(Customer) context.getLoggedInUser();
        System.out.println("\n👤 " + c.getName() +
                " | Wallet: Rs." + c.getWalletBalance());
        System.out.println("1. Browse Products");
        System.out.println("2. View Cart");
        System.out.println("3. Add to Cart");
        System.out.println("4. Place Order");
        System.out.println("5. My Orders");
        System.out.println("6. Logout");
        System.out.print("Choice: ");

        String choice = scanner.nextLine();


        switch(choice){
            case "1" -> customerMenu.browseProducts();
            case "2" -> customerMenu.viewCart(c);
            case "3" -> customerMenu.addToCart(c);
            case "4" -> customerMenu.placeOrder(c);
            case "5" -> customerMenu.myOrders(c);
            case "6" -> {
                context.logout();
                System.out.println("Logged out! ");
            }
            default -> System.out.println("Invalid choice!");
        }
    }
    private void showAdminMenu(){
        System.out.println("\n🔧 Admin Panel");
        System.out.println("1. View All Products");
        System.out.println("2. Add Product");
        System.out.println("3. View All Orders");
        System.out.println("4. Update Order Status");
        System.out.println("5. Logout");
        System.out.print("Choice: ");

        String choice = scanner.nextLine();


        switch(choice){
            case "1" -> adminMenu.viewProducts();
            case "2" -> adminMenu.addProduct();
            case "3" -> adminMenu.viewOrders();
            case "4" -> adminMenu.updateOrderStatus();
            case "5" -> {
                context.logout();
                System.out.println("Logged out!");
            }
            default -> System.out.println("Invalid choice!");
        }
    }
}
