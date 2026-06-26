package com.oms.observer;
import com.oms.model.order.Order;

public class EmailNotifier implements OrderObserver {
    private String emailAddress;

    public EmailNotifier(String emailAddress)
    {
        this.emailAddress=emailAddress;
    }
    @Override
    public void update(Order order) {
        System.out.printf("  [EMAIL → %s]  Your order %s is now: %s%n", emailAddress, order.getOrderId(), order.getStatus());
    }
}
