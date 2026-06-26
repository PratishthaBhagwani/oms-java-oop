package com.oms.observer;
import com.oms.model.order.Order;

public class SmsNotifier implements OrderObserver{
    private String phoneNumber;

    public SmsNotifier(String phoneNumber){
        this.phoneNumber=phoneNumber;
    }
    @Override
    public void update(Order order){
        System.out.printf("  [SMS → %s] Order %s status: %s | Amount: Rs.%.2f%n",
                phoneNumber,
                order.getOrderId(),
                order.getStatus(),
                order.getTotalAmount());

    }
}
