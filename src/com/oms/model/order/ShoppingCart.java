package com.oms.model.order;
import java.util.ArrayList;

public class ShoppingCart
{
    private String customerId;
    private ArrayList<CartItem> items;

    public ShoppingCart(String customerId)
    {
        this.customerId=customerId;
        this.items=new ArrayList<>();
    }
    public void addItem(CartItem item)
    {
        items.add(item);
    }
    public void removeItem(int index)
    {
        items.remove(index);
    }
    public double getTotal()
    {
        double total=0;
        for(CartItem item : items){
                total+=item.getSubtotal();}
        return total;
    }
    public ArrayList<CartItem> getItems()
    {
        return items;
    }
    public String getCustomerId()
    {return customerId;  }
    public boolean isEmpty()
    {
        return items.isEmpty();
    }
    public void displayCart()
    {
        if(isEmpty())
        {
            System.out.println("Cart is Empty !");
            return;
        }
        System.out.println("  --- YOUR CART ---");
        for(CartItem item : items)
        {
            System.out.println(item);
        }
        System.out.println("  -----------------");
        System.out.printf("    TOTAL :  Rs.%.2f%n",getTotal());
    }
}
