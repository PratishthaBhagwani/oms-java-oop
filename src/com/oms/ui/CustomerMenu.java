package com.oms.ui;

import com.oms.app.AppContext;
import com.oms.model.order.*;
import com.oms.model.product.Product;
import com.oms.model.user.Customer;
import com.oms.observer.EmailNotifier;
import com.oms.observer.SmsNotifier;
import com.oms.payment.*;

import java.util.ArrayList;
import java.util.Scanner;

public class CustomerMenu {
    private Scanner scanner;
    private AppContext context;
    private ShoppingCart cart;

    public CustomerMenu(Scanner scanner,AppContext context){
        this.scanner=scanner;
        this.context=context;
    }

    public void browseProducts(){
        System.out.println("\n--- All Products ---");
        ArrayList<Product> products=context.getProducts();
        for(int i=0;i< products.size();i++){
            System.out.println((i + 1) + ". " + products.get(i));
        }
    }
    public void viewCart(Customer c){
        if(cart==null || cart.isEmpty()){
            System.out.println("Cart is empty!");
            return;
        }
        cart.displayCart();
    }
    public void addToCart(Customer c){
        browseProducts();

        System.out.print("Enter product number: ");
        int choice=Integer.parseInt(scanner.nextLine());

        ArrayList<Product> products=context.getProducts();
        if(choice<1 || choice>products.size()){
            System.out.println("Invalid choice!");
            return;
        }
        System.out.print("Enter quantity: ");
        int qty=Integer.parseInt(scanner.nextLine());

        Product selected= products.get(choice-1);

        if(cart==null){
            cart=new ShoppingCart(c.getUserId());
        }
        cart.addItem(new CartItem(selected, qty));
        System.out.println("Added: " + selected.getName() + " x" + qty);

    }
    public void placeOrder(Customer c) {
        if (cart == null || cart.isEmpty()) {
            System.out.println("Cart is empty!");
            return;
        }
        cart.displayCart();
        System.out.println("\nSelect Payment:");
        System.out.println("1. UPI");
        System.out.println("2. Credit Card");
        System.out.println("3. Wallet (Rs." +
                c.getWalletBalance() + ")");
        System.out.println("4. Cash on Delivery");
        System.out.print("Choice: ");

        String payChoice = scanner.nextLine();
        PaymentStrategy payment;
        String methodName;

        switch (payChoice) {
            case "1" -> {
                System.out.print("Enter UPI ID: ");
                String upi = scanner.nextLine();
                payment = new UpiPayment(upi);
                methodName = "UPI";
            }
            case "2" -> {
                System.out.print("Card number: ");
                String num = scanner.nextLine();
                System.out.print("Card holder: ");
                String holder = scanner.nextLine();
                payment = new CreditCardPayment(num, holder);
                methodName = "Credit Card";
            }
            case "3" -> {
                payment = new WalletPayment(c.getUserId(), c.getWalletBalance());
                methodName = "Wallet";
            }
            case "4" -> {
                payment    = new CashOnDelivery();
                methodName = "COD";
            }
            default -> {
                System.out.println("Invalid! Using COD.");
                payment = new UpiPayment("COD");
                methodName = "COD";
            }
        }
        boolean paid = payment.pay(cart.getTotal());
        if (!paid) {
            System.out.println("Payment failed!");
            return;
        }
        System.out.print("Shipping address: ");
        String address = scanner.nextLine();

        Order order = new Order.Builder()
                .orderId("ORD-" + System.currentTimeMillis())
                .customerId(c.getUserId())
                .items(cart.getItems())
                .totalAmount(cart.getTotal())
                .paymentMethod(methodName)
                .shippingAddress(address)
                .build();

        order.subscribe(new EmailNotifier(c.getEmail()));
        order.subscribe(new SmsNotifier("9999900000"));

        context.addOrder(order);
        cart = null;

        System.out.println("\n✅ Order placed!");
        System.out.println(order);

        order.updateStatus(OrderStatus.CONFIRMED);
    }

        public void myOrders(Customer c){
            System.out.println("\n--- My Orders ---");
            boolean found=false;

            for(Order o : context.getOrders()){
                if(o.getCustomerId().equals(c.getUserId())){
                    System.out.println(o);
                    found=true;
                }
            }
            if(!found)  System.out.println("No orders yet!");
        }
    }


