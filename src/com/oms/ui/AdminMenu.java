package com.oms.ui;

import com.oms.app.AppContext;
import com.oms.factory.ProductFactory;
import com.oms.model.order.Order;
import com.oms.model.order.OrderStatus;
import com.oms.model.product.Product;

import java.util.Scanner;

public class AdminMenu {

    private Scanner scanner;
    private AppContext context;

    public AdminMenu(Scanner scanner, AppContext context){
        this.scanner=scanner;
        this.context=context;
    }
    public void viewProducts(){
        System.out.println("\n--- All Products ---");
        for (Product p : context.getProducts()) {
            System.out.println(p);
    }}
    public void addProduct(){
        System.out.println("\nProduct Type:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        System.out.println("3. Grocery");
        System.out.print("Choice: ");
        String type = scanner.nextLine();

        System.out.print("Name: ");
        String name= scanner.nextLine();
        System.out.print("Base Price: ");
        double price=Double.parseDouble(scanner.nextLine());
        System.out.print("Stock: ");
        int stock=Integer.parseInt(scanner.nextLine());

        Product p=null;
        switch(type){
            case "1" ->{
                System.out.print("Warranty (months): ");
                int warranty=Integer.parseInt(scanner.nextLine());
                p=ProductFactory.createElectronics(name,price,stock,warranty);
            }
            case "2" -> {
                System.out.print("Size: ");
                String size=scanner.nextLine();
                p=ProductFactory.createClothing(name,price,stock,size);
            }
            case "3" -> {
                p=ProductFactory.createGrocery(name,price,stock);
            }
            default -> {
                System.out.println("Invalid type!");
                return;
            }
        }
        if(p==null) return;
        context.addProduct(p);
        System.out.println("✅ Product added: " + p.getName() +
                " | ID: " + p.getProductId());

    }
    public void viewOrders()
    {
        System.out.println("\n--- All Orders ---");
        if (context.getOrders().isEmpty()) {
            System.out.println("No orders yet!");
            return;
        }
        for (Order o : context.getOrders()) {
            System.out.println(o);
        }
    }
    public void updateOrderStatus() {
    viewOrders();
    if(context.getOrders().isEmpty()) return;
    System.out.print("Enter Order ID: ");
    String orderId=scanner.nextLine();

    Order found=null;
    for(Order o : context.getOrders()){
        if(o.getOrderId().equals(orderId)){
            found=o;
            break;
        }
    }
    if(found==null){
        System.out.println("Order not found!");
        return;
    }
        System.out.println("Current status: " + found.getStatus());
        System.out.println("1. CONFIRMED");
        System.out.println("2. SHIPPED");
        System.out.println("3. DELIVERED");
        System.out.println("4. CANCELLED");
        System.out.print("Choice: ");
        String choice = scanner.nextLine();
        switch (choice) {
            case "1" -> found.updateStatus(OrderStatus.CONFIRMED);
            case "2" -> found.updateStatus(OrderStatus.SHIPPED);
            case "3" -> found.updateStatus(OrderStatus.DELIVERED);
            case "4" -> found.updateStatus(OrderStatus.CANCELLED);
            default  -> System.out.println("Invalid!");
        }
    }
}

