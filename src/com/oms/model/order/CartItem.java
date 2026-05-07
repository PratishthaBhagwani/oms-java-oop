package com.oms.model.order;
import com.oms.model.product.Product;

public class CartItem {

    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity)
    {
        this.product=product;
        this.quantity=quantity;
    }
    public Product getProduct(){ return product; }
    public int getQuantity(){  return quantity;  }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getSubtotal()
    {
        return product.getFinalPrice()*quantity;
    }
    @Override
    public String toString()
    {
        return String.format("  %-25s x%d = Rs.%.2f",product.getName(), quantity, getSubtotal());
    }
}
