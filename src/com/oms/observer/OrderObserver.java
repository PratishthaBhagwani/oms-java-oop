package com.oms.observer;
import com.oms.model.order.Order;

public interface OrderObserver {
void update(Order order);

}
