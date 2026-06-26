package com.oms.model.order;
import com.oms.observer.OrderObserver;
import java.util.ArrayList;

public class Order {
    private String orderId;
    private String customerId;
    private ArrayList<CartItem> items;
    private double totalAmount;
    private String paymentMethod;
    private String shippingAddress;
    private OrderStatus status;

    private Order(Builder builder){
        this.orderId=builder.orderId;
        this.customerId=builder.customerId;
        this.items=builder.items;
        this.totalAmount=builder.totalAmount;
        this.paymentMethod=builder.paymentMethod;
        this.shippingAddress=builder.shippingAddress;
        this.status=OrderStatus.PENDING;
    }
    private ArrayList<OrderObserver> observers=new ArrayList<>();


    public void subscribe(OrderObserver observer){
        observers.add(observer);
    }
    public void unsubscribe(OrderObserver observer){
        observers.remove(observer);
    }
    public void notifyObservers(){
        for(OrderObserver observer:observers)
        {
            observer.update(this);
        }
    }
    public void updateStatus(OrderStatus newStatus){
        this.status=newStatus;
        notifyObservers();
    }
    public void setStatus(OrderStatus status){
        this.status=status;
    }
    public String getOrderId()           { return orderId; }
    public String getCustomerId()        { return customerId; }
    public ArrayList<CartItem> getItems(){ return items; }
    public double getTotalAmount()       { return totalAmount; }
    public String getPaymentMethod()     { return paymentMethod; }
    public String getShippingAddress()   { return shippingAddress; }
    public OrderStatus getStatus()       { return status; }
    @Override
    public String toString()
    {
        return String.format("Order[%s] | Customer: %s | Rs.%.2f | %s | Status: %s",orderId,customerId,totalAmount,paymentMethod,status);
    }

    public static class Builder {
        private String orderId;
        private String customerId;
        private ArrayList<CartItem> items;
        private double totalAmount;
        private String paymentMethod;
        private String shippingAddress;

        public Builder orderId(String orderId) {
            this.orderId = orderId;
            return this;
        }

        public Builder customerId(String customerId) {
            this.customerId = customerId;
            return this;
        }

        public Builder items(ArrayList<CartItem> items) {
            this.items = items;
            return this;
        }

        public Builder totalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder paymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder shippingAddress(String shippingAddress) {
            this.shippingAddress = shippingAddress;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }



    }


